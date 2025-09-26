<template>
  <div v-if="show" class="overlay">
    <div class="dialog">
      <h3>{{ title }}</h3>
      <p>{{ message }}</p>
      <div class="buttons">
        <button class="btn cancel" @click="cancel">Cancelar</button>
        <button class="btn confirm" @click="confirm">Confirmar</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { defineEmits, defineProps } from 'vue';

const props = defineProps({
  show: Boolean,
  title: String,
  message: String,
});

const emits = defineEmits(['confirm', 'cancel']);

function confirm() {
  emits('confirm');
}

function cancel() {
  emits('cancel');
}
</script>

<style scoped>
.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0,0,0,0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.dialog {
  background: #1f2937;
  color: #f9fafb;
  padding: 2rem;
  border-radius: 10px;
  max-width: 400px;
  text-align: center;
  box-shadow: 0 4px 10px rgba(0,0,0,0.3);
}

.buttons {
  margin-top: 1.5rem;
  display: flex;
  justify-content: space-around;
}

.btn {
  padding: 0.6rem 1.2rem;
  border-radius: 6px;
  border: none;
  cursor: pointer;
  font-weight: 500;
}

.btn.cancel {
  background-color: #6b7280; 
  color: #fff;
}

.btn.confirm {
  background-color: #dc2626;
  color: #fff;
}
</style>
