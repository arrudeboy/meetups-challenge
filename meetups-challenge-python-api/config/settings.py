class Settings:
    PROJECT_NAME:str = "meetups-challenge-python-api"
    PROJECT_VERSION:str = "1.0.0"

    DATABASE_URL:str = "postgresql://postgres:123456@localhost:5432/meetups"

    KEYCLOAK_URL:str = "http://localhost:8082/auth/"
    KEYCLOAK_REALM:str = "MeetupsChallenge"
    
    KEYCLOAK_TOKENS_CLIENT_ID:str = "java-meetups-challenge-api-tokens"
    
    KEYCLOAK_ADMIN_USERNAME:str = "admin"
    KEYCLOAK_ADMIN_PASSWORD:str = "admin"
    
settings = Settings()
