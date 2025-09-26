<script setup>
import { ref, onMounted } from "vue";

import SearchBar from "./SearchBar.vue";
import UserTable from "./UserTable.vue";
import api from "../services/api";
import UserDialog from "./UserDialog.vue";

/**
 * Variáveis reativas para o componente
 */
const users = ref([]);
const currentPage = ref(0);
const totalPages = ref(0);
const pageSize = ref(20);
const searchTerm = ref("");
const showAddUserDialog = ref(false);
const selectedUser = ref(null)

function openAddUserDialog(user = null) {
  selectedUser.value = user
  showAddUserDialog.value = true;
}

function closeAddUserDialog() {
  showAddUserDialog.value = false;
  selectedUser.value = null
}

/**
 * Carrega a lista de usuários do servidor com paginação e filtro de busca
 */
async function fetchUsers(page = 0) {
  try {
    const { data } = await api.get("/users", {
      params: {
        page,
        size: pageSize.value,
        searchTerm: searchTerm.value,
      },
    });
    users.value = data.content;
    totalPages.value = data.totalPages;
    currentPage.value = data.number;
  } catch (err) {
    console.error("Erro ao carregar usuários", err);
  }
}

/**
 * Passar para a próxima página
 */
function nextPage() {
  if (currentPage.value < totalPages.value - 1) {
    fetchUsers(currentPage.value + 1);
  }
}

/**
 * Voltar para a página anterior
 */
function prevPage() {
  if (currentPage.value > 0) {
    fetchUsers(currentPage.value - 1);
  }
}

/**
 * Realizar busca de usuários
 * @param term Termo de busca
 */
function handleSearch(term) {
  searchTerm.value = term;
  fetchUsers(0);
}

/**
 * Escuta o evento disparado pelo dialog de usuário em caso de atualização ou criação
 */
function handleUserAdded() {
  searchTerm.value = ""
  fetchUsers()
}

onMounted(() => {
  fetchUsers();
});
</script>

<template>
  <div class="page-container">
    <header class="page-header">
      <h2>Gerenciamento de Usuários</h2>
    </header>

    <SearchBar @search="handleSearch" />
    <div class="actions">
      <div>
        <label for="pageSize">Usuários por página:</label>
        <input type="number" v-model="pageSize" min="1" max="20" @change="fetchUsers(0)" />
      </div>
      <div>
        <button class="btn add-user" @click="() => openAddUserDialog(null)">
          Adicionar Usuário
        </button>
      </div>
    </div>
    <UserTable :users="users" @edit-user="openAddUserDialog" />

    <div class="pagination">
      <button @click="prevPage" :disabled="currentPage === 0">Anterior</button>
      <span>Página {{ currentPage + 1 }} de {{ totalPages }}</span>
      <button @click="nextPage" :disabled="currentPage === totalPages - 1">
        Próxima
      </button>
    </div>
  </div>
  <UserDialog :user="selectedUser" :show="showAddUserDialog" @close="closeAddUserDialog" @saved="handleUserAdded" />
</template>

<style scoped>
.page-container {
  max-width: 1000px;

  margin: 0 auto;
  padding: 20px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 12px;
  margin-top: 16px;
}

.pagination button {
  padding: 6px 12px;
  border: none;
  background: #42b883;
  color: white;
  cursor: pointer;
  border-radius: 4px;
}

.pagination button:disabled {
  background: #ccc;
  cursor: not-allowed;
}

.actions {
  display: flex;
  gap: 20px;
  justify-content: flex-end;
  margin-bottom: 12px;
}

.actions .btn.add-user {
  background: #42b883;
  color: white;
  border: none;
  padding: 8px 16px;
  border-radius: 4px;
  cursor: pointer;
}

.actions .btn.add-user:hover {
  background: #369870;
}
</style>
