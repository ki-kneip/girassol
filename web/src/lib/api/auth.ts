import { api } from './client';

export type Usuario = {
	id: string;
	nome: string;
	email: string;
};

export function cadastrar(dados: { nome: string; email: string; senha: string }) {
	return api<Usuario>('/auth/cadastro', { method: 'POST', body: dados });
}

export function login(dados: { email: string; senha: string }) {
	return api<Usuario>('/auth/login', { method: 'POST', body: dados });
}

export function logout() {
	return api<void>('/auth/logout', { method: 'POST' });
}

/** Também é o bootstrap do app: a primeira resposta traz o cookie XSRF-TOKEN. */
export function me() {
	return api<Usuario>('/me');
}
