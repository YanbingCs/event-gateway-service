package com.proxzone.cloud.event.core.db;

import com.google.inject.AbstractModule;

import javax.swing.plaf.basic.DefaultMenuLayout;

/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 19-7-3 下午4:31
 */
public class DatabaseModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(RelationalDatabase.class).to(DefaultIciqlMySQLDatabase.class);
        bind(MetaDataBase.class).to(DefaultEtcdMetaDataBase.class);
    }
}
