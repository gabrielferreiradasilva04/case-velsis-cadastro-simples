<template>
  <footer class="footer">
    <p>2025 - Gerenciamento de Usuários</p>
    <div class="contact-info">
      <a href="mailto:gabrielferreirasilva04@outlook.com">gabrielferreirasilva04@outlook.com</a>
      <a href="https://github.com/gabrielferreiradasilva04" target="_blank">GitHub</a>
      <a href="tel:+5541996241805">(41) 99624-1805</a>
    </div>
  </footer>
</template>

<style scoped>
.footer {
  background-color: #1f2937;
  color: #f9fafb;
  text-align: center;
  padding: 1.5rem 1rem;
  font-size: 0.95rem;
  border-top: 2px solid #4b5563;
}

.contact-info {
  margin-top: 0.5rem;
  display: flex;
  justify-content: center;
  gap: 1.5rem;
  flex-wrap: wrap;
}

.contact-info a {
  color: #f9fafb;
  text-decoration: none;
  font-weight: 500;
  transition: color 0.3s;
}

.contact-info a:hover {
  color: #60a5fa;
}
</style>
