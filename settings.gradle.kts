rootProject.name = "UnikPay"

sequenceOf(
    "api",
    "plugin",
).forEach {
    val project = ":unikpay-$it"
    include(project)
    project(project).projectDir = file(it)
}