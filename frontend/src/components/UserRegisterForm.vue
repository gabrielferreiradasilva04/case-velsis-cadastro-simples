<script setup>
import { ref, defineEmits, defineProps, watch } from 'vue'
import api from '../services/api'

const emits = defineEmits(['user-added', 'close'])
const props = defineProps({
  show: Boolean
})

const newUser = ref({
  name: '',
  document: '',
  addressLine: '',
  addressNumber: '',
  city: '',
  state: '',
  zip: '',
  birthDate: ''
})

// Limpar formulário sempre que abrir o dialog
watch(() => props.show, (val) => {
  if(val) {
    newUser.value = {
      name: '',
      document: '',
      addressLine: '',
      addressNumber: '',
      city: '',
      state: '',
      zip: '',
      birthDate: ''
    }
  }
})

async function submitNewUser() {
  try {
    await api.post('/users', newUser.value)
    emits('user-added')
    emits('close')
  } catch (err) {
    console.error('Erro ao salvar usuário', err)
  }
}

function cancel() {
  emits('close')
}
</script>

<template>
  <div v-if="props.show" class="dialog-overlay">
    <div class="dialog">
      <h3>Adicionar Novo Usuário</h3>
      <form @submit.prevent="submitNewUser">
        <div class="form-group">
          <label>Nome</label>
          <input v-model="newUser.name" type="text" required />
        </div>
        <div class="form-group">
          <label>Documento</label>
          <input v-model="newUser.document" type="text" required />
        </div>
        <div class="form-group">
          <label>Data de Nascimento</label>
          <input v-model="newUser.birthDate" type="text" placeholder="dd/mm/yyyy" required />
        </div>
        <div class="form-group">
          <label>Rua</label>
          <input v-model="newUser.addressLine" type="text" />
        </div>
        <div class="form-group">
          <label>Número</label>
          <input v-model="newUser.addressNumber" type="number" />
        </div>
        <div class="form-group">
          <label>Cidade</label>
          <input v-model="newUser.city" type="text" />
        </div>
        <div class="form-group">
          <label>Estado</label>
          <input v-model="newUser.state" type="text" />
        </div>
        <div class="form-group">
          <label>CEP</label>
          <input v-model="newUser.zip" type="text" />
        </div>

        <div class="form-actions">
          <button type="submit" class="btn submit">Salvar</button>
          <button type="button" class="btn cancel" @click="cancel">Cancelar</button>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped>
.dialog-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0,0,0,0.4);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}
.dialog {
  background: white;
  padding: 20px;
  border-radius: 8px;
  min-width: 400px;
}
.form-group {
  margin-bottom: 12px;
}
.form-group label {
  display: block;
  font-weight: 600;
  margin-bottom: 4px;
}
.form-group input {
  width: 100%;
  padding: 6px 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}
.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 8px;
  margin-top: 12px;
}
.btn.submit { background: #42b883; color: white; border: none; padding: 6px 12px; border-radius: 4px; cursor: pointer; }
.btn.submit:hover { background: #369870; }
.btn.cancel { background: #ccc; border: none; padding: 6px 12px; border-radius: 4px; cursor: pointer; }
</style>
