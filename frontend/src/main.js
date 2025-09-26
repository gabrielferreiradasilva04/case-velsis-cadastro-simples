import { createApp } from "vue";
import "./style.css";
import App from "./App.vue";
import VueTheMask from "vue-the-mask";
import Toast from "vue-toastification";
import "vue-toastification/dist/index.css";

const app = createApp(App);

/**
 * Configurações do toast de notificação
 */
const toastOptions = {
  position: "top-right",
  timeout: 5000,
  closeOnClick: true,
  pauseOnHover: true,
  draggable: true,
};

app.use(VueTheMask); // Biblioteca do vue para ajudar na criação de máscaras
app.use(Toast, toastOptions); // Biblioteca do vue para ajudar no controle dos toasts
app.mount("#app");
