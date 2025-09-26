#buildar
FROM node:20 AS build
WORKDIR /app
COPY . .
RUN npm install && npm run build

#subir com nginx
FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]