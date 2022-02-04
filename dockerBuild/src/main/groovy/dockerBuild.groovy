def execute(String s) {
    final File file = new File("${workingDir}")
    println()
    println("Executing script: ${s}")
    println()
    def strings = ["path=" + System.getenv().get("Path"), "COMPUTERNAME=" + System.getenv().get("COMPUTERNAME")]
    def proc = s.execute(strings, file)
    proc.consumeProcessOutput(System.out, System.err)
    int code = proc.waitFor()
    if (code != 0) {
        Thread.sleep(5000) // to allow enough time to print results and error
        throw new IllegalStateException(String.format("Process failed with status code %s!", code))
    }
    println()
}
//System.getenv().each { k, v -> println(k + "=" + v) }
userprofile = System.getenv("USERPROFILE")

execute "docker build -t spring-microservices-build:latest -f ./Dockerfile .."
execute "docker run -v ${userprofile}/.m2/repository2:/root/.m2 -i spring-microservices-build:latest ${executionScript}"

Thread.sleep(5000) // to allow enough time to print results and error