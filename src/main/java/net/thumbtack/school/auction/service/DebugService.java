package net.thumbtack.school.auction.service;

import net.thumbtack.school.auction.dao.CommonDao;
import net.thumbtack.school.auction.daoimpl.CommonDaoImpl;

public class DebugService {
    private final CommonDao commonDao = new CommonDaoImpl();
    public void clear() {
        commonDao.clear();
    }
}
