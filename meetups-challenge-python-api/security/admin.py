from keycloak import KeycloakAdmin

from config.settings import settings

keycloak_admin = KeycloakAdmin(
    server_url = settings.KEYCLOAK_URL,
    username = settings.KEYCLOAK_ADMIN_USERNAME,
    password = settings.KEYCLOAK_ADMIN_PASSWORD,
    realm_name = settings.KEYCLOAK_REALM,
    verify = True
)

user_role = keycloak_admin.get_realm_role("meetup_user")

def get_kc_user_id_by_username(username: str):
    kc_user = keycloak_admin.get_users(query={"username": username})
    if kc_user is None:
        print("User with id %d not found in Keycloak db")

    return kc_user[0]["id"]

def create_kc_user(username: str, email: str, password: str):
    user_data = {"email": email, "username": username, "enabled": True, "credentials": [{"value": password,"type": "password"}],"realmRoles": ["meetup_user"]}
    kc_user_id =  keycloak_admin.create_user(user_data)
    if kc_user_id:
        print("User created in keycloak.")
        keycloak_admin.assign_realm_roles(kc_user_id, [user_role])
    else:
        print("Failed creating user in keycloak")

def update_kc_user(username: str, email: str, password: str):
    kc_user_id = get_kc_user_id_by_username(username)
    keycloak_admin.update_user(kc_user_id, {"email": email, "credentials": [{"value": password,"type": "password"}]})

def delete_kc_user(username: str):
    kc_user_id = get_kc_user_id_by_username(username)
    keycloak_admin.delete_user(kc_user_id)