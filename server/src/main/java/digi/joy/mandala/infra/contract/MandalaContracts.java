package digi.joy.mandala.infra.contract;

import digi.joy.mandala.infra.exception.MandalaRuntimeException;
import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

@UtilityClass
public class MandalaContracts {
    public static <X extends Throwable> void require(Boolean precondition) throws X {
        require(precondition, MandalaRuntimeException::new);
    }

    public static <X extends Throwable> void require(Boolean precondition, Supplier<? extends X> exceptionSupplier) throws X {
        if (!precondition) {
            throw exceptionSupplier.get();
        }
    }

    public static <X extends Throwable> void invariant(Boolean condition) throws X {
        invariant(condition, MandalaRuntimeException::new);
    }

    public static <X extends Throwable> void invariant(Boolean condition, Supplier<? extends X> exceptionSupplier) throws X {
        if (!condition) {
            throw exceptionSupplier.get();
        }
    }

    public static <X extends Throwable> void ensure(Boolean postcondition) throws X {
        ensure(postcondition, MandalaRuntimeException::new);
    }

    public static <X extends Throwable> void ensure(Boolean postcondition, Supplier<? extends X> exceptionSupplier) throws X {
        if (!postcondition) {
            throw exceptionSupplier.get();
        }
    }
}
