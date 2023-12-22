package com.minis.jdbc.batis;

public interface SqlSessionFactory {
    SqlSession openSession();
    MapperNode getMapperNode();
}
