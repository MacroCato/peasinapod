package com.example.peasinapod.Data.Adapter;

public interface GenericDTOAdapter<T, U> {
    U convertToDTO(T entity);
}
