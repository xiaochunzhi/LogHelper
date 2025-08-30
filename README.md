# LogHelper 集成指南

Add it in your settings.gradle.kts at the end of repositories:
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
        repositories {
             mavenCentral()
             maven { url = uri("https://jitpack.io") }
        }
}
Step 2. Add the dependency
    dependencies {
        implementation("com.github.xiaochunzhi:LogHelper:v")
    }

使用：
aliplication 里初始化LogHelperUtils.initialize(this)

在创建okhttpclient的时候添加
.addInterceptor(LoggerInterceptor())
.addInterceptor(ChuckerInterceptorHelper.createChuckerInterceptor(LogHelperUtils.getAppContext()))
全局请使用com.zyc.loghelper.LogUtils