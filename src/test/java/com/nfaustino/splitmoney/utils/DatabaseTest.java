package com.nfaustino.splitmoney.utils;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.junit5.api.DBRider;

@DBRider
@DBUnit(schema = "public", caseSensitiveTableNames = true)
public abstract class DatabaseTest {

}
