<script setup>
const props = defineProps({
  users: Array
})
const emit = defineEmits(['edit-user', 'delete-user'])

function editUser(user) {
  emit('edit-user', user)
}

function deleteUser(user) {
  emit('delete-user', user)
}
</script>

<template>
  <div class="table-container">
    <table class="user-table">
      <thead>
        <tr>
          <th>Nome</th>
          <th>Documento</th>
          <th>Rua</th>
          <th>Número</th>
          <th>Cidade</th>
          <th>Estado</th>
          <th>Código Postal</th>
          <th>Data de Criação</th>
          <th>Última Alteração</th>
          <th>Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="user in props.users" :key="user.id">
          <td>{{ user.name }}</td>
          <td>{{ user.document }}</td>
          <td>{{ user.addressLine }}</td>
          <td>{{ user.addressNumber }}</td>
          <td>{{ user.city }}</td>
          <td>{{ user.state }}</td>
          <td>{{ user.zip }}</td>
          <td>{{ user.createdAt }}</td>
          <td>{{ user.updatedAt }}</td>
          <td>
            <button class="btn edit" @click="editUser(user)">Editar</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
.table-container {
  overflow-x: auto;
  margin-top: 1rem;
}

.user-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 14px;
  background: #fff;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 6px rgba(0,0,0,0.1);
}

.user-table th {
  background: #f5f5f5;
  text-align: left;
  padding: 12px;
  font-size: 12px;
  font-weight: 600;
  color: #333;
}

.user-table td {
  padding: 10px 12px;
  border-top: 1px solid #eee;
  min-width: 100px;
  max-width: 150px;
}

.user-table th:last-child,
.user-table td:last-child {
  position: sticky;
  right: 0;
  background: #fff;
  z-index: 1;
}

.user-table tr:nth-child(even) {
  background: #fafafa;
}

.user-table tr:hover {
  background: #f0f8ff;
}

.btn {
  padding: 6px 12px;
  margin-right: 6px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 13px;
}

.btn.edit {
  background: #1976d2;
  color: white;
}

.btn.edit:hover {
  background: #1565c0;
}

.btn.delete {
  background: #e53935;
  color: white;
}

.btn.delete:hover {
  background: #c62828;
}


</style>
