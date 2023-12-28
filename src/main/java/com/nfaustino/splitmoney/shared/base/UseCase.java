package com.nfaustino.splitmoney.shared.base;

public abstract class UseCase<In, Out> {
    public abstract Out execute(In input);
}
