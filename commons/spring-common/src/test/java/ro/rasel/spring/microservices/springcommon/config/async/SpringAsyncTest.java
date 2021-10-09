package ro.rasel.spring.microservices.springcommon.config.async;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.concurrent.CompletableFuture;

// not a real test, just for understanding how Async in Spring combines with completable future and non blocking
@ContextConfiguration(
        classes = {SpringAsyncTest.A.class, SpringAsyncTest.B.class, SpringAsyncTest.C.class, SpringAsyncTest.D.class,
                SpringAsyncConfigurer.class})
@ExtendWith(SpringExtension.class)
@EnableAsync
class SpringAsyncTest {
    @Autowired
    A a;

    @Test
    public void test() {
        a.a().thenAccept(s -> System.out.println("Result: " + s + " received on " + Thread.currentThread()));
        sleep(20000);
        System.out.println("timeout");
    }

    @TestComponent
    public static class A {
        private final B b;

        public A(B b) {
            this.b = b;
        }

        public CompletableFuture<String> a() {
            try {
                System.out.println("Starting a on thread " + Thread.currentThread());
                return this.b.b().thenApply(s -> {
                    System.out.println("Starting a after response from b on thread " + Thread.currentThread());
                    sleep(1000);
                    System.out.println("Finishing a after response from b");
                    return "DoneA" + s;
                });
            } finally {
                System.out.println("Finishing a");
            }
        }
    }

    @TestComponent
    public static class B {
        private final C c;
        private final D d;

        public B(C c, D d) {
            this.c = c;
            this.d = d;
        }

        @Async
        public CompletableFuture<String> b() {
            try {
                System.out.println("Starting b on thread " + Thread.currentThread());
                return this.c.c()
                        .thenCombine(this.d.d(),
                                (s, s2) -> {
                                    System.out.println("Start combine c and d on thread " + Thread.currentThread());
                                    return s + s2;
                                })
                        .thenApply(s -> {
                            System.out.println("Starting b after combine c and d on thread " + Thread.currentThread());
                            sleep(1000);
                            System.out.println("Finishing b after response from c");
                            return "DoneB" + s;
                        });
            } finally {
                System.out.println("Finishing b");
            }
        }
    }

    @TestComponent
    public static class C {

        @Async
        public CompletableFuture<String> c() {
            System.out.println("Starting c on thread " + Thread.currentThread());
            sleep(5000);
            System.out.println("Finishing c");
            return new AsyncResult<>("DoneC").completable();
        }
    }

    @TestComponent
    public static class D {

        @Async
        public CompletableFuture<String> d() {
            System.out.println("Starting d on thread " + Thread.currentThread());
            sleep(5000);
            System.out.println("Finishing d");
            return new AsyncResult<>("DoneD").completable();
        }
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
