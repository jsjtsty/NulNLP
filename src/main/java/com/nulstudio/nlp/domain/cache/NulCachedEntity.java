package com.nulstudio.nlp.domain.cache;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public interface NulCachedEntity<T> extends Serializable {
    @NotNull T restore();
}
