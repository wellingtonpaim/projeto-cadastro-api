# 🚀 Sistema Web de Cadastro e Gestão de Ordens de Serviço

Este projeto é uma aplicação web completa desenvolvida para gerenciar o cadastro de produtos, clientes, fornecedores e a emissão de ordens de serviço (OS). O sistema conta com uma arquitetura moderna, utilizando segurança via JWT, validações robustas de documentos (CPF/CNPJ) e uma interface responsiva focada na experiência do usuário.

---

## 🛠️ Tecnologias Utilizadas

### **Backend**
- **Java 21**: Utilizando os recursos mais recentes da linguagem.
- **Spring Boot 3.4.3**: Framework base para a API REST.
- **Spring Data JPA**: Para persistência e manipulação de dados.
- **Spring Security & JWT**: Autenticação e autorização baseada em tokens.
- **PostgreSQL**: Banco de dados relacional para produção.
- **Thymeleaf & OpenPDF**: Geração dinâmica de relatórios de OS em formato PDF.
- **ModelMapper**: Mapeamento eficiente entre entidades e DTOs.
- **Bean Validation**: Validação rigorosa de dados de entrada (incluindo CPF/CNPJ customizado).

### **Frontend**
- **React 19**: Biblioteca principal para a interface.
- **Vite**: Ferramenta de build e desenvolvimento de alta performance.
- **TypeScript**: Tipagem estática para maior manutenibilidade.
- **Tailwind CSS 4**: Estilização moderna e utilitária.
- **React Router Dom**: Navegação entre páginas.
- **Axios**: Comunicação com a API Backend.
- **Lucide React**: Conjunto de ícones vetoriais.

---

## 📋 Funcionalidades Principais

- **Segurança**: Fluxo de login completo, proteção de rotas e expiração de token.
- **Módulo de Clientes e Fornecedores**:
  - Cadastro com validação de documentos.
  - Gestão de múltiplos telefones e endereços vinculados.
- **Catálogo de Produtos**:
  - Organização por famílias/categorias.
  - Vínculo direto com fornecedores.
- **Ordens de Serviço (OS)**:
  - Adição dinâmica de itens/produtos.
  - Cálculo de mão de obra e descontos personalizáveis.
  - Geração de documento PDF profissional para o cliente.
- **Administração**: Cadastro de usuários com níveis de acesso e confirmação por e-mail.

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
- JDK 21 instalado.
- Node.js (versão 18 ou superior).
- Banco de dados PostgreSQL configurado.
Parte 2: Configuração, Execução e Docker
Markdown
### Configuração do Backend
1. Navegue até a pasta `/Backend`.
2. Configure as credenciais do banco de dados no arquivo `src/main/resources/application-dev.properties`:
   - `spring.datasource.url`: URL do seu banco Postgres.
   - `spring.datasource.username`: Seu usuário.
   - `spring.datasource.password`: Sua senha.
3. (Opcional) Configure as variáveis de e-mail para o sistema de confirmação de cadastro.
4. Execute a aplicação:
   ```bash
   ./mvnw spring-boot:run
A API estará disponível em http://localhost:8888.
5. Documentação da API (Swagger): http://localhost:8888/swagger-ui.html.

Configuração do Frontend
Navegue até a pasta /Frontend.

Instale as dependências necessárias:

Bash
npm install
Inicie o ambiente de desenvolvimento:

Bash
npm run dev
O frontend estará disponível em http://localhost:5173.

🐳 Docker
O projeto está pronto para ser containerizado. Para gerar a imagem do backend:

Bash
cd Backend
docker build -t sistema-cadastro-backend .
📂 Estrutura de Pastas
Backend/: Código fonte Java/Spring Boot, configurações de segurança e geração de PDF.

Frontend/: Aplicação React com Vite, componentes Tailwind e definições TypeScript.

📄 Licença
Este projeto foi desenvolvido como parte de um Projeto Integrador acadêmico. Sinta-se à vontade para clonar, estudar e contribuir.

Desenvolvido por Wellington Paim.
