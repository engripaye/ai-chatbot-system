# Complete AI Customer Support Chatbot System using Spring Boot
## Spring AI with OpenRouter, 
WebSocket and REST, MongoDB, and a React/HTML frontend. The system will include smart auto-responses, human handoff for low-confidence AI responses, role-based support agents (customer, agent, admin), and an admin dashboard to monitor chats. I’ll use Java 21 and Spring Boot 3.3.2 (latest stable version as of July 21, 2025, as Spring Boot 3.5 is not widely confirmed), noting adjustments for 3.5 if applicable. This guide is detailed and beginner-friendly, with explanations for each step.

Overview
The system will:
1.  Use Spring Boot for the backend, with Spring AI integrating OpenRouter’s API for AI-powered responses.
2.  Implement WebSocket for real-time chat and REST for admin and user management.
3.  Store chat data in MongoDB for persistence.
4.  Provide a React frontend for customers, agents, and admins, with a dashboard to monitor chats.
5.  Support smart auto-responses with confidence scoring, human handoff, and role-based access.

Prerequisites
1.  Java 21: Install OpenJDK 21 (e.g., via SDKMAN: sdk install java 21-open).
2.  Maven: For dependency management.
3.  OpenRouter API Key: Sign up at OpenRouter and generate an API key.
4.  MongoDB: Install locally (MongoDB Community Server) or use MongoDB Atlas.
5.  Node.js/NPM: For the React frontend (install from http://nodejs.org).
6.  IDE: IntelliJ IDEA, Eclipse, or VS Code.
7.  Postman or curl: For testing REST APIs.

Step 1: Set Up OpenRouter
OpenRouter provides access to various LLMs via a unified API, making it a flexible choice for AI responses.
1.  Get an API Key:
•  Sign up at OpenRouter.
•  Navigate to the dashboard, click “Keys” in the sidebar, and create a new API key.
•  Save the key securely.

2.  Test the API:
•  Use curl to verify:
curl -X POST https://openrouter.ai/api/v1/chat/completions \
-H "Authorization: Bearer YOUR_API_KEY" \
-H "Content-Type: application/json" \
-d '{"model": "meta-llama/llama-3.2-8b-instruct", "messages": [{"role": "user", "content": "Hello, how can I assist you today?"}]}'
•  Expect a JSON response with the AI’s reply.

Explanation:
•  OpenRouter supports models like meta-llama/llama-3.2-8b-instruct, which is suitable for customer support.
•  The API key and model will be configured in Spring AI.

Step 2: Project Setup
Create a Spring Boot project using Spring Initializr.
1.  Create Project:
•  Go to http://start.spring.io.
•  Configure:
•  Project: Maven
•  Language: Java
•  Spring Boot: 3.3.2 (latest stable; adjust to 3.5.3 if confirmed available)
•  Java: 21
•  Group: com.example
•  Artifact: chatbot-system
•  Dependencies:
•  Spring Web
•  Spring WebFlux (for WebSocket)
•  Spring Data MongoDB
•  Lombok
•  Generate, download, unzip, and open in your IDE.
2.  Add Spring AI Dependency: Since Spring AI does not have a specific OpenRouter starter, we’ll use the generic spring-ai-openai-spring-boot-starter (OpenRouter’s API is compatible with OpenAI’s format)
