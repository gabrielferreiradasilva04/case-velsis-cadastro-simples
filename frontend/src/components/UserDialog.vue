<script setup>
import { ref, defineEmits, defineProps, watch, computed } from 'vue'
import api from '../services/api'
import { useToast } from "vue-toastification";
import ConfirmDialog from './ConfirmDialog.vue';

const toast = useToast();
const showDialog = ref(false);

/***
 * Eventos de emissão
 */
const emits = defineEmits(['saved', 'close', 'user-added'])
const props = defineProps({
  show: Boolean,
  user: { type: Object, default: null }
})

const docType = ref('cpf')

/***
 * Usuário padrão
*/
const defaultUser = {
  name: '',
  document: '',
  addressLine: '',
  addressNumber: '',
  city: '',
  state: '',
  zip: '',
  birthDate: ''
}

/***
 * Cria uma cópia do usuário passado para atualizar os dados
 */
const userForm = ref({ ...defaultUser })

/**
 * Iniciar o formulário de acordo com o parâmetro user
 */
watch(() => props.show, (val) => {
  if (val) {
    userForm.value = props.user ? { ...props.user } : { ...defaultUser }
    docType.value = props.user?.document?.length >= 11 ? 'rg' : 'cpf'
  }
})


function handleConfirm() {
  showDialog.value = false; 
  deleteUser();
}

function openConfirmDialog() {
  showDialog.value = true;
}

function handleCancel() {
  showDialog.value = false;
}

async function deleteUser() {
  if (!props.user) return;
  try {
    await api.delete(`/users/${props.user.id}`);
    emits('saved');
    emits('close');
    toast.success('Usuário excluído com sucesso!');
    showDialog.value = false;
  } catch (err) {
    console.error('Erro ao excluir usuário', err);
    toast.error('Erro ao excluir usuário. Tente novamente mais tarde.');
  }
}

/**
 * Função para salvar ou atualizar o usuário
 */
async function submitUser() {
  try {
    if (props.user) {
      await api.put(`/users/${props.user.id}`, { ...userForm.value })
    } else {
      await api.post('/users', { ...userForm.value })
    }

    emits('saved')
    emits('close')
    emits('user-added')

    toast.success(`Usuário ${props.user ? 'atualizado' : 'salvo'} com sucesso!`)
  } catch (err) {
    console.error('Erro ao salvar usuário', err);
    const message = err.response?.data?.message || 'Erro ao salvar usuário. Verifique os dados e tente novamente.';
    toast.error(message);
  }
}


/**
 * Máscaras de entrada
 */
const docMask = computed(() => docType.value === 'cpf' ? '###.###.###-##' : '##.###.###-#')
const dateMask = '##/##/####'
const zipMask = '#####-###'

function cancel() {
  emits('close')
}

const estados = [
  'AC', 'AL', 'AP', 'AM', 'BA', 'CE', 'DF', 'ES', 'GO', 'MA', 'MT', 'MS', 'MG',
  'PA', 'PB', 'PR', 'PE', 'PI', 'RJ', 'RN', 'RS', 'RO', 'RR', 'SC', 'SP', 'SE', 'TO'
]
</script>

<template>
  <div v-if="props.show" class="dialog-overlay">
    <div class="dialog">
      <h3>{{ props.user ? 'Atualizar Usuário' : 'Cadastrar Novo Usuário' }}</h3>
      <form @submit.prevent="submitUser" class="form-grid">
        <div class="form-group">
          <label>Nome*</label>
          <input v-model="userForm.name" type="text" required />
        </div>
        <div class="form-group">
          <label>Tipo de Documento</label>
          <select v-model="docType" required>
            <option value="cpf">CPF</option>
            <option value="rg">RG</option>
          </select>
        </div>
        <div class="form-group">
          <label>Documento*</label>
          <input v-model="userForm.document" v-mask="docMask" type="text" required />
        </div>
        <div class="form-group">
          <label>Data de Nascimento*</label>
          <input v-model="userForm.birthDate" type="text" v-mask="dateMask" placeholder="dd/mm/yyyy" required />
        </div>
        <div class="form-group">
          <label>Rua*</label>
          <input v-model="userForm.addressLine" type="text" required />
        </div>
        <div class="form-group">
          <label>Número*</label>
          <input v-model="userForm.addressNumber" type="number" required />
        </div>
        <div class="form-group">
          <label>Cidade*</label>
          <input v-model="userForm.city" type="text" required />
        </div>
        <div class="form-group">
          <label>Sigla Estado*</label>
          <select v-model="userForm.state" required>
            <option value="" disabled>Selecione</option>
            <option v-for="estado in estados" :key="estado" :value="estado">{{ estado }}</option>
          </select>
        </div>
        <div class="form-group">
          <label>CEP*</label>
          <input v-model="userForm.zip" v-mask="zipMask" type="text" required />
        </div>
        <div class="form-actions">
          <button type="submit" class="btn submit">{{ props.user ? 'Atualizar' : 'Salvar' }}</button>
          <button type="button" class="btn remove" @click="openConfirmDialog" v-show="props.user">Excluir</button>
          <button type="button" class="btn cancel" @click="cancel">Cancelar</button>          
        </div>
      </form>
    </div>
  </div>
  <ConfirmDialog
    :show="showDialog"
    title="Confirmação"
    :message="`Deseja realmente excluir ${props.user?.name || ''}?`"
    @confirm="handleConfirm"
    @cancel="handleCancel"
  />
</template>

<style scoped>
.dialog-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.4);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.dialog {
  background: white;
  padding: 24px 32px;
  border-radius: 8px;
  min-width: 600px;
  box-sizing: border-box;
}

.dialog h3 {
  text-align: center;
  margin-bottom: 20px;
}

.form-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 16px 24px;
}

.form-group {
  display: flex;
  flex-direction: column;
}

.form-group label {
  font-weight: 600;
  margin-bottom: 6px;
}

.form-group input,
.form-group select {
  width: 100%;
  padding: 8px 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
  background-color: white;
}

.form-actions {
  grid-column: span 2;
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  margin-top: 16px;
}

.btn.submit {
  background: #42b883;
  color: white;
  border: none;
  padding: 6px 12px;
  border-radius: 4px;
  cursor: pointer;
}

.btn.submit:hover {
  background: #369870;
}

.btn.remove {
  background: #b84242;
  color: white;
  border: none;
  padding: 6px 12px;
  border-radius: 4px;
  cursor: pointer;
}

.btn.remove:hover {
  background: #983636;
}
.btn.remove:disabled {
  background: #ccc;
  cursor: not-allowed;
}

.btn.cancel {
  background: #ccc;
  border: none;
  padding: 6px 12px;
  border-radius: 4px;
  cursor: pointer;
}
</style>
