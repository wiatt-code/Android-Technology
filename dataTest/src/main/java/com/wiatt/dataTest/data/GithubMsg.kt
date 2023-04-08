package com.wiatt.dataTest.data

/**
 * content 只能填入OwnerDb、RepoDb
 */
data class GithubMsg(var type: String, var content: Any) {
    companion object {
        const val TYPE_OWNER = "type_owner"
        const val TYPE_REPO = "type_repo"
    }
}