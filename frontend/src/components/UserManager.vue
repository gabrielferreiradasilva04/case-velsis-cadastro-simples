<script setup>
import { ref, onMounted } from "vue";

import SearchBar from "./SearchBar.vue";
import UserTable from "./UserTable.vue";
import api from "../services/api";
import UserRegisterForm from "./UserRegisterForm.vue";

const users = ref([]);
const currentPage = ref(0);
const totalPages = ref(0);
const pageSize = ref(20);
const searchTerm = ref("");
const showAddUserDialog = ref(false);

function openAddUserDialog() {
  showAddUserDialog.value = true;
}

function closeAddUserDialog() {
  showAddUserDialog.value = false;
}

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

function nextPage() {
  if (currentPage.value < totalPages.value - 1) {
    fetchUsers(currentPage.value + 1);
  }
}

function prevPage() {
  if (currentPage.value > 0) {
    fetchUsers(currentPage.value - 1);
  }
}

function handleSearch(term) {
  searchTerm.value = term;
  fetchUsers(0);
}
function handleUserAdded() {
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

    <SearchBar
      @search="handleSearch"
    />
    <div class="actions">
      <button class="btn add-user" @click="openAddUserDialog">
        Adicionar Usuário
      </button>
    </div>
    <UserTable :users="users" />

    <div class="pagination">
      <button @click="prevPage" :disabled="currentPage === 0">Anterior</button>
      <span>Página {{ currentPage + 1 }} de {{ totalPages }}</span>
      <button @click="nextPage" :disabled="currentPage === totalPages - 1">
        Próxima
      </button>
    </div>
  </div>
  <UserRegisterForm :show="showAddUserDialog" @close="closeAddUserDialog" @user-added="handleUserAdded" />
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
