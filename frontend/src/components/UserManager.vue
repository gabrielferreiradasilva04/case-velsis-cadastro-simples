<script setup>
import { ref, onMounted } from 'vue'

import SearchBar from './SearchBar.vue'
import UserTable from './UserTable.vue'

const users = ref([])
const currentPage = ref(0)
const totalPages = ref(0)
const pageSize = ref(10)
const searchTerm = ref('')

async function fetchUsers(page = 0) {
  try {
    const { data } = await api.get('/users', {
      params: {
        page,
        size: pageSize.value,
        search: searchTerm.value
      }
    })
    users.value = data.content
    totalPages.value = data.totalPages
    currentPage.value = data.number
  } catch (err) {
    console.error('Erro ao carregar usuários', err)
  }
}

function nextPage() {
  if (currentPage.value < totalPages.value - 1) {
    fetchUsers(currentPage.value + 1)
  }
}

function prevPage() {
  if (currentPage.value > 0) {
    fetchUsers(currentPage.value - 1)
  }
}

onMounted(() => {
  fetchUsers()
})
</script>

<template>
  <div class="page-container">
    <header class="page-header">
      <h2>Gerenciamento de Usuários</h2>
    </header>

    <SearchBar @search="term => { searchTerm.value = term; fetchUsers(0) }" />

    <UserTable :users="users" />

    <div class="pagination">
      <button @click="prevPage" :disabled="currentPage === 0">Anterior</button>
      <span>Página {{ currentPage + 1 }} de {{ totalPages }}</span>
      <button @click="nextPage" :disabled="currentPage === totalPages - 1">Próxima</button>
    </div>
  </div>
</template>

<style scoped>
.page-container {
  max-width: 900px;
  margin: 0 auto;
  padding: 20px;
}

.page-header {
  margin-bottom: 20px;
  text-align: center;
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
</style>
