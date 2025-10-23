package com.trader.identity.service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Component;

import com.trader.identity.repository.PlayerRepository;

@Component
public class RandomNameGenerator {

    private final PlayerRepository playerRepository;

    public RandomNameGenerator(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    private final List<String> casinoWords = List.of(
            // Trading classics
            "ChartChaser", "CandleKing", "BullishBeast", "BearBreaker", "WhaleWatcher",
            "TrendTactician", "ScalpSamurai", "SwingSensei", "ProfitPilot", "MarketMarauder",
            "FiboFighter", "BreakoutBandit", "RiskRanger", "SpreadSniper", "MarginMonk",
            "ShortShark", "PipPirate", "EquityEagle", "OrderOgre", "LiquidityLynx",
            "PricePredator", "TradeTitan", "SignalSlinger", "VolumeViper", "StopLossSoldier",
            "ChartCrusader", "TradeTornado", "GreedGuru", "PatternPanther", "BuyDipBandit",
            "SellHighSage", "EntryEmperor", "ExitExecutioner", "VolatilityViking", "CandleCrusader",
            "PositionPanda", "LimitLord", "StopHunter", "PipProphet", "TradeTactician",

            // Crypto-trading mashups
            "SatoshiScalper", "BlockBroker", "HodlHawk", "TokenTrader", "DeFiDealer",
            "ArbApe", "GasGambler", "SmartContractScalper", "WalletWhale", "CoinCrafter",
            "AltcoinAlchemist", "ChainChartist", "OrderOnChain", "DEXDuelist", "LiquidityLooter",
            "PerpPunter", "MarginMiner", "FuturesFox", "SpotSlayer", "SwapSlinger",
            "YieldYakuza", "RugRadar", "PumpPilot", "DumpDefender", "TradeOnChain",
            "DAODealer", "FiatFader", "HashHedger", "LedgerLurker", "TokenTactician",
            "ATHAssassin", "BuyTheDipBot", "MoonMogul", "GasGuru", "StakeStrategist",
            "RektRescuer", "ArbArtist", "L2Legend", "ChainChad", "RollupRogue",

            // Funny & edgy trader nicknames
            "PaperHandPal", "DiamondFingers", "FOMOFiend", "ApeInvestor", "PumpPenguin",
            "DumpDuck", "RektRaccoon", "ChartChimp", "ScalpSquirrel", "ProfitPossum",
            "BearBunny", "CandleCrab", "MoonMantis", "StopLossSeal", "OrderOtter",
            "EntryEel", "ExitEagle", "LiquidityLlama", "FiboFerret", "MarginMoose",
            "CryptoCamel", "TokenTiger", "SwapShark", "TrendTurtle", "BreakoutBat",
            "DeFiDingo", "YieldYak", "SpreadSpider", "PositionParrot", "ArbAnt",
            "ChartCoyote", "TradeToad", "HodlHedgehog", "PumpPanda", "VolumeVulture",
            "PipPig", "CoinCoyote", "RiskRaven", "ProfitPigeon", "DegenDuck",

            // Meme-trader mashups
            "WAGMIWhale", "NGMIScalper", "RektRider", "HODLHero", "MoonMissionary",
            "BearMarketBrawler", "BullRunBandit", "ATHAddict", "DiamondDealer", "PaperPunter",
            "ApesTogetherTrade", "NotYourKeysTrader", "LedgerLad", "StonkStaker", "PumpPilotPro",
            "RuggedRanger", "DegenDaytrader", "SwingPsychopath", "SellSignalSavage", "BuyButtonBasher",
            "LiquidityLegend", "FarmFiend", "YieldYolo", "PositionPumper", "DCADeity",

            // Badass / outlaw traders
            "TradeTyrant", "ChartAssassin", "ScalpSlayer", "MarketMarauder", "SpreadSniperX",
            "ProfitPunisher", "LimitLegend", "FuturesFiend", "RiskReaper", "CandleKiller",
            "StopLossSamurai", "ATHAvenger", "PumpPirate", "ShortSniper", "LiquidityLord",
            "MarginMonster", "TradeTerminator", "CryptoCrusader", "BearBane", "BullBuster",
            "OrderOutlaw", "RektReaper", "WhaleWarlord", "ChartChampion", "ExecutionExpert",
            "PatternPredator", "VolatilityVandal", "GreedGunner", "FOMOFragger", "PipPunisher");

    public String generate() {
        String base = casinoWords.get(ThreadLocalRandom.current().nextInt(casinoWords.size()));
        int number = ThreadLocalRandom.current().nextInt(1000, 9999);
        return base + "_" + number;
    }

    public String generateUniqueUsername() {
        String username;
        do {
            username = generate();
        } while (playerRepository.existsByUsername(username));
        return username;
    }
}