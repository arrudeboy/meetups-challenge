from fastapi.security import OAuth2AuthorizationCodeBearer
from keycloak import KeycloakOpenID
from config.settings import settings
from fastapi import Security, HTTPException, status, Depends
from pydantic import Json
from functools import wraps

keycloak_url = settings.KEYCLOAK_URL
keycloak_realm = settings.KEYCLOAK_REALM

oauth2_scheme = OAuth2AuthorizationCodeBearer(
    authorizationUrl = keycloak_url,
    tokenUrl = f"{keycloak_url}realms/{keycloak_realm}/protocol/openid-connect/token"
)

keycloak_openid = KeycloakOpenID(
    server_url = keycloak_url,
    client_id = settings.KEYCLOAK_TOKENS_CLIENT_ID,
    realm_name = keycloak_realm,
    verify = True
)

async def get_idp_public_key():
    return (
        "-----BEGIN PUBLIC KEY-----\n"
        f"{keycloak_openid.public_key()}"
        "\n-----END PUBLIC KEY-----"
    )

async def get_auth(token: str = Security(oauth2_scheme)) -> Json:
    try:
        return keycloak_openid.decode_token(
            token,
            key = await get_idp_public_key(),
            options = {
                "verify_signature": True,
                "verify_aud": False,
                "exp": True
            }
        )
    except Exception as e:
        raise HTTPException(
            status_code = status.HTTP_401_UNAUTHORIZED,
            detail = str(e),  # "Invalid authentication credentials",
            headers = {"WWW-Authenticate": "Bearer"},
        )

def required_roles(*roles):
    def decorator(func):
        @wraps(func)
        def check(*args, **kwargs):
            user_roles = kwargs['identity']['realm_access']['roles']
            if not any(ur in roles for ur in user_roles):
                raise HTTPException(
                    status_code = status.HTTP_401_UNAUTHORIZED,
                    detail = "User has no permissions to perform this operation",
                    headers = {"WWW-Authenticate": "Bearer"},
                )
            return func(*args, **kwargs)
        return check
    return decorator
