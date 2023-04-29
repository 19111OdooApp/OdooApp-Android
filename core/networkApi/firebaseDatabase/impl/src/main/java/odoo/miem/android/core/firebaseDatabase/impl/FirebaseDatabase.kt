package odoo.miem.android.core.firebaseDatabase.impl

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import io.reactivex.rxjava3.core.Single
import odoo.miem.android.core.networkApi.firebaseDatabase.api.IFirebaseDatabase
import odoo.miem.android.core.networkApi.firebaseDatabase.api.source.ModuleIconResponse
import odoo.miem.android.core.networkApi.firebaseDatabase.api.source.StatusIconResponse
import timber.log.Timber
import javax.inject.Inject

/**
 * [FirebaseDatabase] - wrapper of Firebase Firestore and Storage for fetching icons
 *
 * @author Egor Danilov
 */
class FirebaseDatabase @Inject constructor() : IFirebaseDatabase {

    private val fireStore: FirebaseFirestore by lazy {
        Firebase.firestore
    }
    private val storage: FirebaseStorage by lazy {
        Firebase.storage
    }

    override fun fetchModuleIcons(): Single<List<ModuleIconResponse>> {
        return Single.create { emitter ->
            fireStore.collection(FIRESTORE_MODULE_COLLECTION)
                .get()
                .continueWithTask { query ->
                    val iconTasks = mutableListOf<Task<ModuleIconResponse>>()

                    query.result.map { module ->
                        val moduleName = module.data[MODULE_NAME_FIELD] as? String
                        val iconUrl = module.data[MODULE_ICON_URL_FIELD] as? String

                        iconUrl?.let {
                            val task = fetchFileFromStorage(it)
                                .continueWith { task ->
                                    ModuleIconResponse(
                                        moduleName = moduleName,
                                        downloadUrl = task.result.toString()
                                    )
                                }
                                .addOnFailureListener { e ->
                                    Timber.e("fetchFileFromStorage(): error = ${e.message}")
                                }

                            iconTasks.add(task)
                        }
                    }

                    Tasks.whenAllSuccess<ModuleIconResponse>(iconTasks)
                }
                .addOnSuccessListener { result ->
                    Timber.d("fetchModulesIcons(): icons = $result")
                    emitter.onSuccess(result)
                }
                .addOnFailureListener { e ->
                    Timber.e("fetchModuleIcons(): error = ${e.message}")
                    emitter.onError(e)
                }
        }
    }

    override fun fetchStatusIcons(): Single<List<StatusIconResponse>> {
        return Single.create { emitter ->
            fireStore.collection(FIRESTORE_STATUS_COLLECTION)
                .get()
                .continueWithTask { query ->
                    val iconTasks = mutableListOf<Task<StatusIconResponse>>()

                    query.result.map { module ->
                        val moduleName = module.data[STATUS_NAME_FIELD] as? String
                        val iconUrl = module.data[STATUS_ICON_URL_FIELD] as? String

                        iconUrl?.let {
                            val task = fetchFileFromStorage(it)
                                .continueWith { task ->
                                    StatusIconResponse(
                                        statusName = moduleName,
                                        downloadUrl = task.result.toString()
                                    )
                                }
                                .addOnFailureListener { e ->
                                    Timber.e("fetchFileFromStorage(): error = ${e.message}")
                                }

                            iconTasks.add(task)
                        }
                    }

                    Tasks.whenAllSuccess<StatusIconResponse>(iconTasks)
                }
                .addOnSuccessListener { result ->
                    Timber.d("fetchStatusIcons(): icons = $result")
                    emitter.onSuccess(result)
                }
                .addOnFailureListener { e ->
                    Timber.e("fetchStatusIcons(): error = ${e.message}")
                    emitter.onError(e)
                }
        }
    }

    private fun fetchFileFromStorage(fileReference: String): Task<Uri> {
        return storage.getReferenceFromUrl(fileReference).downloadUrl
    }

    private companion object {
        const val FIRESTORE_MODULE_COLLECTION = "module_icons"

        const val MODULE_NAME_FIELD = "module_name"
        const val MODULE_ICON_URL_FIELD = "icon_url"

        const val FIRESTORE_STATUS_COLLECTION = "status_icons"

        const val STATUS_NAME_FIELD = "status_name"
        const val STATUS_ICON_URL_FIELD = "icon_url"
    }
}
