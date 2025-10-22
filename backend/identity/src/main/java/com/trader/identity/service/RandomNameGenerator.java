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
            // Casino classics
            "AceVentura", "ChipMonster", "SpinItToWinIt", "SnakeEyes", "FlushRush",
            "LuckyDuck", "Bluffinator", "RouletteRocket", "CashOutCat", "HotStreak",
            "DiceyBusiness", "Betzilla", "JackpotJester", "AllInAndy", "FoldyMcFoldface",
            "StackAttack", "PitBossy", "HouseAlwaysWins", "SevenHeaven", "SlotStorm",
            "BigBlindBandit", "TurnBurner", "RiverRunner", "DealerStealer", "TokenTaker",
            "RakeRaccoon", "AnteEater", "ShowdownShark", "GoldenChip", "SpinDoctor",
            "BonusBuffalo", "MarkerMarauder", "LuckyCharmander", "CasinoCactus", "ChippyChappy",
            "DoubleDowner", "LottoLegend", "CashCow", "LuckyBreak", "SpinCycle",
            "OddsOnTop", "RiskItAll", "BetMaxx", "HighStakes", "TrueOdds",

            // Crypto + casino mashups
            "SatoshiDice", "BlockJack", "HodlHighroller", "TokenTornado", "DeFiDealer",
            "RollTheChain", "BetOnBlockchain", "GasFeeGambler", "LuckyLedger", "CoinFlipKid",
            "CryptoCroupier", "WhaleRoller", "NFTBandit", "HashJackpot", "AltcoinAnte",
            "WalletWarrior", "StakingShark", "MoonshotMaker", "DiceDAO", "CashCowCoin",
            "MempoolMaverick", "ForkFlush", "RugPullRoulette", "PumpNDumpPlayer", "SatoshiSlots",
            "DoubleSpendDice", "ChainlinkChancer", "LedgerLucky", "BlockBet", "HashHustler",
            "SmartContractShuffler", "HodlHouse", "ETHRoller", "GasGuzzler", "MiningManiac",
            "DeFiDice", "RollupRoulette", "BetTheBlock", "ProofOfStakePunter", "FOMOFlush",
            "CryptoCasino", "MoonBagBet", "BetChain", "LamboLuck", "DegenDice",
            "TokenTilter", "DAODealer", "StonkSlots", "DiceOnChain", "BlockBusterBet",
            "NFTNuts", "Layer2Lucker", "LedgerLoot", "BetOnETH", "TokenWhale",
            "HashPot", "Flip2Moon", "CoinCasino", "Roll2Riches", "LuckyGas",
            "CryptoCasinoCat", "LedgerLord", "BlockRoller", "FudFlush", "StakeSnake",

            // Funny & edgy nicknames
            "BetBot9000", "LuckyLambo", "PaperHandPunter", "DiamondDice", "VegasValidator",
            "MoonbagMaverick", "JackpotJunkie", "DegenerateDealer", "LottoLad", "CroupierCoin",
            "CasinoChain", "HighStakesHash", "BigWinBlock", "RollOrRug", "AnteChain",
            "TokenTable", "ChainChips", "StakeMachine", "BonusBandit", "CasinoCrusader",
            "RiskyRoller", "CoinCowboy", "SpinGoblin", "BluffBuffalo", "PayoutPirate",
            "LuckyLobster", "CashCrab", "DiceDragon", "OddsOctopus", "TokenTiger",
            "StackSquirrel", "RakeRhino", "SlotSloth", "CardCoyote", "MarkerMoose",
            "BingoBear", "FOMOFerret", "CryptoCobra", "NFTNarwhal", "PumpPenguin",
            "DumpDuck", "WhaleWolf", "LamboLynx", "DeFiDingo", "SatoshiSeal",
            "ChainChameleon", "StakingStallion", "MoonMonkey", "ValidatorViper", "HotHandHawk",
            "PayoutPanda", "GasGorilla", "LuckyLeopard", "JackpotJaguar", "SpinShark",
            "AnteAnt", "BluffBat", "RollRabbit", "TokenTurtle", "CasinoCrab",

            // Meme casino mashups
            "YOLOBets", "WAGMIDice", "NGMIRoller", "RektRoulette", "MoonMission",
            "HodlHotshot", "DiamondHandsDice", "PaperHandsPlayer", "ApesTogetherBet",
            "NotYourKeysCasino", "LedgerLooter", "DeFiDegen", "CasinoMaxi", "ShillSlots",
            "PampPunter", "DumpDealer", "StakeStonker", "CryptoCasinoCraze", "PumpPlayer",
            "SnipeSpin", "RuggedRoller", "WhaleWatcher", "OnChainChancer", "Bet2Earn",
            "GameFiGambler", "LuckyLiquidity", "FarmFiend", "YieldYolo", "StonkShuffler",

            // Extra filler funny names
            "AnteUpBot", "ChipChomper", "AllInApe", "BetBuffoon", "HighRollHamster",
            "CryptoCamel", "HashHyena", "LuckyLynx", "SpinSkunk", "CasinoCapybara",
            "BlockBunny", "StakeStarfish", "OddsOtter", "DiceDonkey", "PayoutParrot",
            "CasinoCrane", "MoonMoose", "BetBeaver", "CryptoCougar", "TokenToad",
            "JackpotJay", "LuckyLark", "RollRaven", "PumpPigeon", "StackStork",
            "LamboLizard", "HodlHawk", "StakeSeahorse", "CashCactus", "GasGiraffe",

            // Badass / outlaw section
            "BetBadass", "DiceBandit", "CryptoOutlaw", "StakeSavage",
            "RouletteRebel", "TokenTerminator", "AllInAssassin", "HashHunter",
            "WhaleWarrior", "CasinoKiller", "MoonMarauder", "BlockBoss",
            "AnteAnarchist", "PayoutPunisher", "LuckyLegend", "GasGangster",
            "SpinSamurai", "DiceDestroyer", "BluffBarbarian", "JackpotJuggernaut",
            "RakeReaper", "RollRogue", "HighStakesHitman", "DealerDoom", "LedgerLegend",
            "CryptoCrusher", "TokenTyrant", "BlockBrawler", "FOMOFiend", "CashCarnage");

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