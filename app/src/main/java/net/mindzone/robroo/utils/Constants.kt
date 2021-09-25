package net.mindzone.robroo.utils

object Constants {
    const val KEY_BEARER = "Bearer"
    const val PATTERN_PARSE_TIME_SERVER = "yyyy-MM-dd'T'HH:mm:ss"

    const val AZURE_kClientId = "d81e4deb-4282-4108-a137-d2b070976e8f"
    const val AZURE_kRedirectUri = "robroo://azure.oauth"
    const val AZURE_mAuthEndpoint =
        "https://login.microsoftonline.com/eef38a1f-720f-4ede-9c7a-79ef6d5dd342/oauth2/v2.0/authorize"
    const val AZURE_mTokenEndpoint =
        "https://login.microsoftonline.com/eef38a1f-720f-4ede-9c7a-79ef6d5dd342/oauth2/v2.0/token"
    const val AZURE_ChooseAccount = "https://login.microsoftonline.com/appverify"
    const val URL_LOGOUT = "https://login.microsoftonline.com/common/oauth2/v2.0/logout?\n" +
            "post_logout_redirect_uri=http%3A%2F%2Flocalhost%2Fmyapp%2F"
}