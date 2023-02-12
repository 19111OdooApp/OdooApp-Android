plugins {
    conventions.`module-compose-impl`
}

android {
    namespace = "odoo.miem.android.feature.profile.impl"
}

dependencies {

    api(project(":feature:profile:api"))
}