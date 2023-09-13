const { Octokit } = require("octokit");

// Use o token de acesso pessoal para autenticar as chamadas à API do GitHub
const octokit = new Octokit({
  auth: process.argv[2],
});

async function fetchPRStatistics() {
  try {
    const owner = "seu-usuario";
    const repo = "seu-repositorio";

    const { data: pullRequests } = await octokit.rest.pulls.list({
      owner,
      repo,
      state: "open",
    });

    const prCount = pullRequests.length;

    // Você pode fazer mais análises ou gerar mais estatísticas aqui, se desejar.

    console.log(`Quantidade de Pull Requests Abertos: ${prCount}`);
  } catch (error) {
    console.error(error);
    process.exit(1);
  }
}

fetchPRStatistics();