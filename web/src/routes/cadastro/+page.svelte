<script lang="ts">
	import { goto } from '$app/navigation';
	import { cadastrar, login } from '$lib/api/auth';
	import { ApiError } from '$lib/api/client';

	let nome = $state('');
	let email = $state('');
	let senha = $state('');
	let erro = $state('');
	let enviando = $state(false);

	async function enviar(e: SubmitEvent) {
		e.preventDefault();
		erro = '';
		enviando = true;
		try {
			await cadastrar({ nome, email, senha });
			await login({ email, senha });
			await goto('/');
		} catch (err) {
			erro = err instanceof ApiError ? err.message : 'Erro inesperado. Tente de novo.';
		} finally {
			enviando = false;
		}
	}
</script>

<main class="mx-auto flex min-h-screen max-w-sm flex-col justify-center gap-6 p-4">
	<div class="text-center">
		<h1 class="text-3xl font-bold">🌻 Girassol</h1>
		<p class="opacity-70">Crie sua conta</p>
	</div>

	<form class="flex flex-col gap-3" onsubmit={enviar}>
		<label class="flex flex-col gap-1">
			<span class="text-sm font-medium">Nome</span>
			<input class="rounded border p-2" type="text" bind:value={nome} required maxlength="120" />
		</label>
		<label class="flex flex-col gap-1">
			<span class="text-sm font-medium">E-mail</span>
			<input class="rounded border p-2" type="email" bind:value={email} required maxlength="255" />
		</label>
		<label class="flex flex-col gap-1">
			<span class="text-sm font-medium">Senha</span>
			<input class="rounded border p-2" type="password" bind:value={senha} required minlength="8" maxlength="72" />
		</label>

		{#if erro}
			<p class="text-sm text-red-700" role="alert">{erro}</p>
		{/if}

		<button
			class="rounded bg-amber-500 p-2 font-medium text-white disabled:opacity-50"
			type="submit"
			disabled={enviando}
		>
			{enviando ? 'Criando…' : 'Criar conta'}
		</button>
	</form>

	<p class="text-center text-sm">
		Já tem conta? <a class="underline" href="/login">Entrar</a>
	</p>
</main>
