<script lang="ts">
	// Fecha o M0: a resposta que aparece aqui atravessou Vite -> proxy -> Spring -> Postgres
	const health = fetch('/api/actuator/health').then((r) => {
		if (!r.ok) throw new Error(`HTTP ${r.status}`);
		return r.json() as Promise<{ status: string }>;
	});
</script>

<main class="flex min-h-screen flex-col items-center justify-center gap-2">
	<h1 class="text-4xl font-bold">🌻 Girassol</h1>
	<p class="text-lg opacity-70">gira em torno do que importa</p>

	{#await health}
		<p>verificando a API…</p>
	{:then h}
		<p class="font-mono text-green-700">API {h.status}</p>
	{:catch e}
		<p class="font-mono text-red-700">API fora do ar ({e.message})</p>
	{/await}
</main>
