package com.nulstudio.nlp.service.respository;

import com.nulstudio.nlp.domain.cache.CachedAccount;
import com.nulstudio.nlp.entity.NulAccount;
import com.nulstudio.nlp.repository.AccountRepository;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountRepositoryService {

    @Resource
    private AccountRepository accountRepository;

    @Resource
    private CacheManager cacheManager;

    @Cacheable(value = "account", key = "#uid", unless = "#result == null")
    @NotNull
    public Optional<CachedAccount> getAccountByUid(long uid) {
        final Optional<NulAccount> optional = accountRepository.findById(uid);
        return optional.map(CachedAccount::new);
    }

    @Cacheable(value = "username", key = "#username", unless = "#result == null")
    @NotNull
    public Optional<Number> getUidByUsername(@NotNull String username) {
        final Optional<NulAccount> optional = accountRepository.findByUsername(username);
        return optional.map(NulAccount::getId);
    }

    @NotNull
    public CachedAccount saveAccount(@NotNull CachedAccount account) {
        final NulAccount result = accountRepository.save(account.restore());
        final Cache accountCache = cacheManager.getCache("account"),
                userNameCache = cacheManager.getCache("username");
        if (accountCache != null) {
            accountCache.put(result.getId(), result);
        }
        if (userNameCache != null) {
            userNameCache.put(result.getUsername(), account.getId());
        }
        return new CachedAccount(result);
    }
}
