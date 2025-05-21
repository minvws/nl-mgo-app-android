import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

val Project.versionCatalog
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun Project.addBillOfMaterials(bomAlias: String) {
    val bomDependency = versionCatalog.findLibrary(bomAlias).get()
    dependencies {
        val bom = platform(bomDependency)
        add("implementation", bom)
        add("testImplementation", bom)
    }
}
