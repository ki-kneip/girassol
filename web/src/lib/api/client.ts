// Cliente HTTP único da aplicação: prefixo /api (proxy no dev, reverse proxy em prod),
// cookies de sessão e CSRF resolvidos aqui — nenhuma tela chama fetch direto.

const BASE = '/api';

export class ApiError extends Error {
	constructor(
		public readonly status: number,
		public readonly erro: string,
		mensagem: string
	) {
		super(mensagem);
	}
}

function xsrfToken(): string | null {
	const match = document.cookie.match(/(?:^|;\s*)XSRF-TOKEN=([^;]*)/);
	return match ? decodeURIComponent(match[1]) : null;
}

export async function api<T>(
	path: string,
	options: { method?: 'GET' | 'POST' | 'PATCH' | 'DELETE'; body?: unknown } = {}
): Promise<T> {
	const method = options.method ?? 'GET';
	const headers: Record<string, string> = {};

	if (options.body !== undefined) headers['Content-Type'] = 'application/json';
	if (method !== 'GET') {
		let token = xsrfToken();
		if (!token) {
			// Browser sem o cookie XSRF ainda (ex.: primeiro acesso caiu direto num form):
			// qualquer GET ao backend semeia o token
			await fetch(BASE + '/actuator/health');
			token = xsrfToken();
		}
		if (token) headers['X-XSRF-TOKEN'] = token;
	}

	const response = await fetch(BASE + path, {
		method,
		headers,
		body: options.body !== undefined ? JSON.stringify(options.body) : undefined
	});

	if (response.status === 204) return undefined as T;

	const data = await response.json().catch(() => null);
	if (!response.ok) {
		throw new ApiError(
			response.status,
			data?.erro ?? 'erro_desconhecido',
			data?.mensagem ?? `Erro ${response.status}`
		);
	}
	return data as T;
}
