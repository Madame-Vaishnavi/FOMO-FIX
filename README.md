
# FOMO-FIX 🎟️

### A Microservices-based Event Booking Platform

-----

## **About the Project**

**FOMO-FIX** is a robust and scalable event booking application built on a microservices architecture. The platform is designed to provide users with a seamless experience for discovering, booking, and managing various events. The system's backend, built with **Spring Boot**, leverages the power of service discovery, asynchronous communication, and secure authentication to ensure high performance and reliability. The modern user interface is crafted using **Flutter**, allowing for a consistent experience across multiple platforms.

-----

## **Features**

  * **Event Management**: Comprehensive system for creating, updating, and listing events.
  * **Secure Authentication**: **JWT-based authentication** for secure user and administrator access.
  * **Asynchronous Communications**: Utilizes **Apache Kafka** for decoupled, non-blocking operations such as payment processing and notification services.
  * **Real-time Notifications**: Automated email notifications powered by **Gmail SMTP** for booking confirmations and updates.
  * **Custom Payment Gateway**: A dedicated payment service for handling secure transactions.

-----

## **Tech Stack**

### **Backend**

  * **Framework**: Spring Boot
  * **Service Discovery**: Spring Cloud Eureka
  * **Inter-service Communication**: Feign Client
  * **Asynchronous Messaging**: Apache Kafka
  * **Security**: JWT (JSON Web Tokens)
  * **Email Service**: Gmail SMTP

### **Frontend**

  * **Framework**: Flutter

### **Database**

  * **PostgreSQL**

-----

## **Architecture**

The backend of FOMO-FIX is architected as a collection of independently deployable microservices. This design philosophy offers enhanced scalability, resilience, and maintainability.

  * **Service Registry**: The **Eureka Server** acts as the central service registry, enabling microservices to register their locations and discover other services dynamically.
  * **Client Communication**: **Feign Client** simplifies inter-service communication by providing a declarative REST client, reducing boilerplate code.
  * **Event-Driven System**: **Apache Kafka** serves as the message bus for event-driven communication. This ensures that services like the payment gateway and the email notification service operate asynchronously, enhancing system performance and reliability.
  * **Diagram**:

-----

## **Screenshots**

<img src="https://github.com/user-attachments/assets/7cf9b73d-8180-4c80-889d-948d9ed7b686" width= "20%" />
<img src="https://github.com/user-attachments/assets/c06d521d-1eb3-4808-9603-9cf63b6e3315" width= "20%" />
<img src="https://github.com/user-attachments/assets/a91ed1cc-9d9a-4d76-bdf4-09a045e7ed15" width= "20%" />
<img src="https://github.com/user-attachments/assets/3d9d3dc3-621a-4a33-a256-5ae5af2cfd13" width= "20%" />
<img src="https://github.com/user-attachments/assets/a0dbd7b9-9e1c-4f75-8b38-ebe09307dc2a" width= "20%" />
<img src="https://github.com/user-attachments/assets/c0054fe3-1d54-4fb5-a486-313b523a8b33" width= "20%" />


-----

## **Getting Started**

To set up and run the FOMO-FIX project locally, please ensure you have the following prerequisites installed.

### **Prerequisites**

  * **Java Development Kit (JDK) 17+**
  * **Flutter SDK**
  * **Docker & Docker Compose** (recommended for running Kafka and PostgreSQL)
  * **Git**

### **Backend Setup**

1.  **Clone the repository**:
    ```bash
    git clone https://github.com/your-username/fomo-fix.git
    cd fomo-fix/backend
    ```
2.  **Database & Kafka**:
      * It is highly recommended to use Docker Compose to set up your PostgreSQL and Kafka instances. A `docker-compose.yml` file is provided in the repository.
      * Start the containers:
        ```bash
        docker-compose up -d
        ```
3.  **Configuration**:
      * Update the `application.yml` file in each microservice with the correct database, Eureka, and Kafka connection details.
      * Add your Gmail SMTP credentials.
4.  **Build and Run**:
      * Build the project using Maven:
        ```bash
        ./mvnw clean install
        ```
      * Run each microservice as a Spring Boot application from your IDE or the command line.

### **Frontend Setup**

1.  **Navigate to the frontend directory**:
    ```bash
    cd ../frontend
    ```
2.  **Install dependencies**:
    ```bash
    flutter pub get
    ```
3.  **Configure API Endpoints**:
      * Update the API endpoints in the frontend code to point to your locally running backend microservices.
4.  **Run the application**:
    ```bash
    flutter run
    ```

-----

## **Contributing**

Contributions are what make the open-source community an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1.  Fork the Project
2.  Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3.  Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4.  Push to the Branch (`git push origin feature/AmazingFeature`)
5.  Open a Pull Request
