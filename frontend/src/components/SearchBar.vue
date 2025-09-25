<script setup>
import { ref } from 'vue'
const searchTerm = ref('')

const emit = defineEmits(['search'])

function handleSearch() {
  emit('search', searchTerm.value)
}
</script>

<template>
  <div class="search-bar">
    <input 
      v-model="searchTerm" 
      type="text" 
      placeholder="Pesquisar usuário..." 
      @keyup.enter="handleSearch"
    />
    <button @click="handleSearch">Buscar</button>
  </div>
</template>

<style scoped>
.search-bar {
  display: flex;
  gap: 8px;
  margin-bottom: 1rem;
}

.search-bar input {
  flex: 1;
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 14px;
}

.search-bar input:focus {
  outline: none;
  border-color: #1976d2;
  box-shadow: 0 0 4px rgba(25, 118, 210, 0.3);
}

.search-bar button {
  padding: 8px 14px;
  background: #1976d2;
  color: white;
  border: none;
  border-radius: 6px;
  font-size: 14px;
  cursor: pointer;
}

.search-bar button:hover {
  background: #1565c0;
}
</style>
