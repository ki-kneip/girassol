<script lang="ts">
	import { onMount } from 'svelte';
	import { goto } from '$app/navigation';
	import { me, logout, type Usuario } from '$lib/api/auth';

	let usuario = $state<Usuario | null>(null);
	let carregando = $state(true);

	// O /me no boot também semeia o cookie XSRF-TOKEN pro resto do app
	onMount(async () => {
		try {
			usuario = await me();
		} catch {
			usuario = null;
		} finally {
			carregando = false;
		}
	});

	async function sair() {
		await logout();
		usuario = null;
		await goto('/login');
	}
</script>

<main class="flex min-h-screen flex-col items-center justify-center gap-4">
	<h1 class="text-4xl font-bold">🌻 Girassol</h1>
	<p class="text-lg opacity-70">gira em torno do que importa</p>

	{#if carregando}
		<p>carregando…</p>
	{:else if usuario}
		<p>Olá, <strong>{usuario.nome}</strong>!</p>
		<button class="rounded border px-4 py-2" onclick={sair}>Sair</button>
	{:else}
		<div class="flex gap-3">
			<a class="rounded bg-amber-500 px-4 py-2 font-medium text-white" href="/login">Entrar</a>
			<a class="rounded border px-4 py-2" href="/cadastro">Criar conta</a>
		</div>
	{/if}
</main>
