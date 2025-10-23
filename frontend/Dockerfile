FROM node:22-alpine

WORKDIR /app
COPY package*.json ./
RUN npm install

# Keep dev server live with hot reload
COPY . .
EXPOSE 3000
CMD ["npm", "run", "dev"]