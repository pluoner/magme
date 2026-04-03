-- Magic: The Gathering card INSERT statements
-- Table: card
-- Columns: scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness,
--          colors, color_identity, set_code, set_name, rarity, flavor_text

-- ============================================================
-- CREATURES
-- ============================================================

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('00d89839-60d7-4de2-a78a-1afdcc21c053', 'Llanowar Elves', '{G}', 1, 'Creature — Elf Druid', '{T}: Add {G}.', '1', '1', 'G', 'G', 'dom', 'Dominaria', 'common', 'Llanowar Elves are among the greatest natural gifts of Dominaria, a living embodiment of the forest''s vitality.');

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('b7c19924-b4bf-4c96-a052-f86a51bd3dce', 'Savannah Lions', '{W}', 1, 'Creature — Cat', '', '2', '1', 'W', 'W', '2ed', 'Revised Edition', 'uncommon', NULL);

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('cdc46e67-f73a-45f4-9f5d-b81b49bdeb2f', 'Lightning Bolt', '{R}', 1, 'Instant', 'Lightning Bolt deals 3 damage to any target.', NULL, NULL, 'R', 'R', 'm11', 'Magic 2011', 'common', NULL);

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('d3c0d0e4-7b5b-4b0d-a573-2b50df79e6c5', 'Serra Angel', '{3}{W}{W}', 5, 'Creature — Angel', 'Flying, vigilance', '4', '4', 'W', 'W', '2ed', 'Revised Edition', 'uncommon', 'And so Serra''s angels watched over the Null Moon, protecting it against the darkness below.');

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('e37e57ce-4e6e-4b10-8d4c-2dc35de00a86', 'Tarmogoyf', '{1}{G}', 2, 'Creature — Lhurgoyf', 'Tarmogoyf''s power is equal to the number of card types among cards in all graveyards and its toughness is equal to that number plus 1.', '*', '1+*', 'G', 'G', 'fut', 'Future Sight', 'rare', '"Why kill yourself working when it''ll do it for you?" —Saffi Eriksdotter, last words');

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('f9d36b69-96d8-4b7c-b5e3-6af9ec7d7e18', 'Goblin Guide', '{R}', 1, 'Creature — Goblin Scout', 'Haste\nWhenever Goblin Guide attacks, defending player reveals the top card of their library. If it''s a land card, that player puts it into their hand.', '2', '2', 'R', 'R', 'zen', 'Zendikar', 'rare', '"I''ve mapped every mountain pass and river ford. Let''s move." —Jeska, warrior adept');

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('a29d5f2a-7b5b-49ac-831d-38b2d4a09b7b', 'Snapcaster Mage', '{1}{U}', 2, 'Creature — Human Wizard', 'Flash\nWhen Snapcaster Mage enters the battlefield, target instant or sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost.', '2', '1', 'U', 'U', 'isd', 'Innistrad', 'rare', 'Aberrant realities flow through his mind, allowing him to cast spells he could never truly understand.');

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('5f8287b1-5bb6-4388-a14e-af9d84a0d4d6', 'Dark Confidant', '{1}{B}', 2, 'Creature — Human Wizard', 'At the beginning of your upkeep, reveal the top card of your library and put that card into your hand. You lose life equal to its mana value.', '2', '1', 'B', 'B', 'rav', 'Ravnica: City of Guilds', 'rare', 'Greatness, at any cost.');

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('c4e82f53-59fb-4a73-9a3b-52b19e6a1b5d', 'Voice of Resurgence', '{G}{W}', 2, 'Creature — Elemental', 'Whenever an opponent casts a spell during your turn or when Voice of Resurgence dies, create a green and white Elemental creature token with "This creature''s power and toughness are each equal to the number of creatures you control."', '2', '2', 'G,W', 'G,W', 'dgm', 'Dragon''s Maze', 'rare', NULL);

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('4e5b5b1a-7b5b-4b0d-a573-2b50df79e6c6', 'Emrakul, the Aeons Torn', '{15}', 15, 'Legendary Creature — Eldrazi', 'This spell can''t be countered.\nWhen you cast this spell, take an extra turn after this one.\nFlying, protection from colored spells, annihilator 6\nWhen Emrakul, the Aeons Torn is put into a graveyard from anywhere, its owner shuffles their graveyard into their library.', '15', '15', '', '', 'roe', 'Rise of the Eldrazi', 'mythic', 'The first Planeswalker to encounter Emrakul saw his plane collapse into madness. Other Planeswalkers only saw the distant void she left behind.');

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('9b37b16f-c1a1-4c8c-bf11-5a04ed4e0cd0', 'Delver of Secrets', '{U}', 1, 'Creature — Human Wizard', 'At the beginning of your upkeep, look at the top card of your library. You may reveal it. If an instant or sorcery card is revealed this way, transform Delver of Secrets.', '1', '1', 'U', 'U', 'isd', 'Innistrad', 'common', 'A desperate student of forbidden arcane secrets, he sought and found an answer to the riddle of life in one obsessive night of discovery.');

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('d9e4b237-7b5b-4b0d-a573-2b50df79e6c7', 'Thoughtseize', '{B}', 1, 'Sorcery', 'Target player reveals their hand. You choose a nonland, nontoken card from it. That player discards that card. You lose 2 life.', NULL, NULL, 'B', 'B', 'ths', 'Theros', 'rare', 'Ashiok needs no words, only a quick rummage through the labyrinth of your mind.');

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('8a9c5d2e-1f3a-4b6c-9d8e-7f2a1b3c5d4e', 'Elvish Mystic', '{G}', 1, 'Creature — Elf Druid', '{T}: Add {G}.', '1', '1', 'G', 'G', 'm14', 'Magic 2014', 'common', '"Whether I work in forest, field, or town, the land provides."');

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('3c7a9b1d-2e4f-6a8b-0c2d-4e6f8a0b2c4d', 'Siege Rhino', '{1}{W}{B}{G}', 4, 'Creature — Rhino', 'Trample\nWhen Siege Rhino enters the battlefield, each opponent loses 3 life and you gain 3 life.', '4', '5', 'W,B,G', 'W,B,G', 'ktk', 'Khans of Tarkir', 'rare', 'The abzan brought rhinos from the harsh desert conditions to ensure their hardiness and ferocity in battle.');

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('5e7b9c3f-4a6d-8e0f-2b4c-6d8e0f2b4c6d', 'Collected Company', '{3}{G}', 4, 'Instant', 'Look at the top six cards of your library. Put up to two creature cards with mana value 3 or less from among them onto the battlefield. Put the rest on the bottom of your library in any order.', NULL, NULL, 'G', 'G', 'dtk', 'Dragons of Tarkir', 'rare', NULL);

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('7a9d1f5b-6c8e-0a2c-4e6f-8a0b2c4d6e8f', 'Cryptic Command', '{1}{U}{U}{U}', 4, 'Instant', 'Choose two —\n• Counter target spell.\n• Return target permanent to its owner''s hand.\n• Tap all creatures your opponents control.\n• Draw a card.', NULL, NULL, 'U', 'U', 'lrw', 'Lorwyn', 'rare', NULL);

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('2b4d6e8a-0c2e-4f6a-8b0d-2e4f6a8b0c2e', 'Death''s Shadow', '{B}', 1, 'Creature — Avatar', 'Death''s Shadow gets -X/-X, where X is your life total.', '13', '13', 'B', 'B', 'wwk', 'Worldwake', 'rare', NULL);

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('4c6e8f0a-2d4f-6b8c-0e2a-4f6b8c0d2e4f', 'Noble Hierarch', '{G}', 1, 'Creature — Human Druid', 'Exalted\n{T}: Add {G}, {W}, or {U}.', '0', '1', 'G', 'G,W,U', 'con', 'Conflux', 'rare', 'She protects the wild, carries the banner of the meek, and guards the ancient knowledge of the deeply rooted.');

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('6e0b2d4f-8a0c-2e4f-6b8d-0a2c4f6e8b0d', 'Uro, Titan of Nature''s Wrath', '{1}{G}{U}', 3, 'Legendary Creature — Elder Giant', 'When Uro enters the battlefield, sacrifice it unless it escaped.\nWhenever Uro enters the battlefield or attacks, you gain 3 life and draw a card, then you may put a land card from your hand onto the battlefield.\nEscape—{G}{G}{U}{U}, Exile five other cards from your graveyard.', '6', '6', 'G,U', 'G,U', 'thb', 'Theros Beyond Death', 'mythic', NULL);

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('8f1c3e5a-0b2d-4e6f-8c0a-2d4e6f8b0c2d', 'Monastery Swiftspear', '{R}', 1, 'Creature — Human Monk', 'Haste\nProwess', '1', '2', 'R', 'R', 'ktk', 'Khans of Tarkir', 'uncommon', '"No matter how cunningly I planned her moves, I could not anticipate her next strike." —Sidisi, Sultai regent');

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('0d2f4b6e-8a1c-3f5b-7d9e-1a3c5f7b9d1e', 'Bloodbraid Elf', '{2}{R}{G}', 4, 'Creature — Elf Berserker', 'Haste\nCascade', '3', '2', 'R,G', 'R,G', 'arb', 'Alara Reborn', 'uncommon', 'She and her allies would surge through one rift and burst from another, distracting enemies with their unpredictable appearances.');

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('1e3a5c7f-9b1d-3f5a-7c9e-1b3d5f7a9c1e', 'Stoneforge Mystic', '{1}{W}', 2, 'Creature — Kor Artificer', 'When Stoneforge Mystic enters the battlefield, you may search your library for an Equipment card, reveal it, put it into your hand, then shuffle.\n{1}{W}, {T}: You may put an Equipment card from your hand onto the battlefield.', '1', '2', 'W', 'W', 'wwk', 'Worldwake', 'rare', 'She alone could shape the stone of Zendikar''s hedrons.');

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('2f4b6d8e-0a2c-4e6b-8d0f-2a4c6e8b0d2f', 'Rhystic Study', '{2}{U}', 3, 'Enchantment', 'Whenever an opponent casts a spell, you may draw a card unless that player pays {1}.', NULL, NULL, 'U', 'U', 'pcy', 'Prophecy', 'common', '"Pay me, or else."');

-- ============================================================
-- INSTANTS
-- ============================================================

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('3a5c7e9f-1b3d-5a7c-9e1f-3b5d7a9c1e3f', 'Counterspell', '{U}{U}', 2, 'Instant', 'Counter target spell.', NULL, NULL, 'U', 'U', 'me2', 'Masters Edition II', 'common', NULL);

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('4b6d8f0a-2c4e-6b8d-0f2a-4c6e8b0d2f4a', 'Path to Exile', '{W}', 1, 'Instant', 'Exile target creature. Its controller may search their library for a basic land card, put that card onto the battlefield tapped, then shuffle.', NULL, NULL, 'W', 'W', 'con', 'Conflux', 'uncommon', 'To prevent a permanent evil, the Arbiter''s price must be paid.');

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('5c7e9a1b-3d5f-7c9e-1a3b-5d7f9a1c3e5b', 'Fatal Push', '{B}', 1, 'Instant', 'Destroy target creature if it has mana value 2 or less.\nRevolt — Destroy that creature if it has mana value 4 or less instead if a permanent you controlled left the battlefield this turn.', NULL, NULL, 'B', 'B', 'aer', 'Aether Revolt', 'uncommon', 'The Gatewatch pressed Yahenni for details, but the aetherborn only laughed. "You don''t need to know how I did it. You just need to know it''s done."');

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('6d8f0b2c-4e6a-8d0f-2b4c-6e8a0d2f4b6c', 'Brainstorm', '{U}', 1, 'Instant', 'Draw three cards, then put two cards from your hand on top of your library in any order.', NULL, NULL, 'U', 'U', 'ice', 'Ice Age', 'common', '"I may not be able to see the future clearly, but I can at least choose which of several murky visions to pursue." —Jodah, Archmage Eternal');

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('7e9a1c3d-5f7b-9e1a-3c5d-7f9b1e3a5c7d', 'Swords to Plowshares', '{W}', 1, 'Instant', 'Exile target creature. Its controller gains life equal to its power.', NULL, NULL, 'W', 'W', 'lea', 'Limited Edition Alpha', 'uncommon', NULL);

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('8f0b2d4e-6a8c-0f2b-4d6e-8a0c2f4b6d8e', 'Force of Will', '{3}{U}{U}', 5, 'Instant', 'You may pay 1 life and exile a blue card from your hand rather than pay this spell''s mana cost.\nCounter target spell.', NULL, NULL, 'U', 'U', 'all', 'Alliances', 'uncommon', 'Three thousand years of Dominarian wizardry compressed into one unstoppable syllogism.');

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('9a1c3e5f-7b9d-1a3c-5e7f-9b1d3a5c7e9f', 'Ancestral Recall', '{U}', 1, 'Instant', 'Target player draws three cards.', NULL, NULL, 'U', 'U', 'lea', 'Limited Edition Alpha', 'rare', NULL);

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('0b2d4f6a-8c0e-2b4d-6f8a-0c2e4b6d8f0a', 'Mana Leak', '{1}{U}', 2, 'Instant', 'Counter target spell unless its controller pays {3}.', NULL, NULL, 'U', 'U', 'sth', 'Stronghold', 'common', '"I''m afraid the words you need to say will have to wait." —Ertai');

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('1c3e5a7b-9d1f-3c5e-7a9b-1d3f5c7e9a1b', 'Spell Pierce', '{U}', 1, 'Instant', 'Counter target noncreature spell unless its controller pays {2}.', NULL, NULL, 'U', 'U', 'zen', 'Zendikar', 'common', NULL);

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('2d4f6b8c-0e2a-4d6f-8b0c-2e4a6d8f0b2c', 'Abrupt Decay', '{B}{G}', 2, 'Instant', 'This spell can''t be countered.\nDestroy target nonland permanent with mana value 3 or less.', NULL, NULL, 'B,G', 'B,G', 'rtr', 'Return to Ravnica', 'rare', 'The Izzet mages'' frantic gestures and magical words cut short, their eyes wide, before their bodies met the same fate.');

-- ============================================================
-- SORCERIES
-- ============================================================

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('3e5a7c9d-1f3b-5e7a-9c1d-3f5b7e9a1c3d', 'Demonic Tutor', '{1}{B}', 2, 'Sorcery', 'Search your library for a card and put that card into your hand. Then shuffle.', NULL, NULL, 'B', 'B', 'lea', 'Limited Edition Alpha', 'uncommon', NULL);

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('4f6b8d0e-2a4c-6f8b-0d2e-4a6c8f0b2d4e', 'Wrath of God', '{2}{W}{W}', 4, 'Sorcery', 'Destroy all creatures. They can''t be regenerated.', NULL, NULL, 'W', 'W', 'lea', 'Limited Edition Alpha', 'rare', 'And the meek shall inherit... nothing.');

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('5a7c9e1f-3b5d-7a9c-1e3f-5b7d9a1c3e5f', 'Ponder', '{U}', 1, 'Sorcery', 'Look at the top three cards of your library, then put them back in any order. You may shuffle.\nDraw a card.', NULL, NULL, 'U', 'U', 'm12', 'Magic 2012', 'common', '"I look at the choices before me and know that each path will bring growth. The difficulty is knowing which growth I desire." —Jace Beleren');

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('6b8d0f2a-4c6e-8b0d-2f4a-6c8e0b2d4f6a', 'Preordain', '{U}', 1, 'Sorcery', 'Scry 2, then draw a card.', NULL, NULL, 'U', 'U', 'm11', 'Magic 2011', 'common', '"It is a fool who dismisses portents and signs. Often they are warnings. Always they are messages." —Kara Vrist, Neurok spy');

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('7c9e1a3b-5d7f-9c1e-3a5b-7d9f1c3e5a7b', 'Reanimate', '{B}', 1, 'Sorcery', 'Put target creature card from a graveyard onto the battlefield under your control. You lose life equal to its mana value.', NULL, NULL, 'B', 'B', 'tmp', 'Tempest', 'rare', NULL);

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('8d0f2b4c-6e8a-0d2f-4b6c-8e0a2d4f6b8c', 'Dark Ritual', '{B}', 1, 'Instant', 'Add {B}{B}{B}.', NULL, NULL, 'B', 'B', 'lea', 'Limited Edition Alpha', 'common', '"Power is an illusion...whisper the word and the illusion becomes reality." —Lim-Dûl the Necromancer');

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('9e1a3c5d-7f9b-1e3a-5c7d-9f1b3e5a7c9d', 'Time Walk', '{1}{U}', 2, 'Sorcery', 'Take an extra turn after this one.', NULL, NULL, 'U', 'U', 'lea', 'Limited Edition Alpha', 'rare', NULL);

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('0f2b4d6e-8a0c-2f4b-6d8e-0a2c4f6b8d0e', 'Green Sun''s Zenith', '{X}{G}', 1, 'Sorcery', 'Search your library for a green creature card with mana value X or less, put it onto the battlefield, then shuffle. Shuffle Green Sun''s Zenith into its owner''s library.', NULL, NULL, 'G', 'G', 'mbs', 'Mirrodin Besieged', 'rare', 'The pinnacle of the phyrexian sky, the point where all the suns converged.');

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('1a3c5e7f-9b1d-3a5c-7e9f-1b3d5a7c9e1f', 'Living Death', '{3}{B}{B}', 5, 'Sorcery', 'Each player exiles all creature cards from their graveyard, then sacrifices all creatures they control, then puts all cards they exiled this way onto the battlefield.', NULL, NULL, 'B', 'B', 'tmp', 'Tempest', 'rare', 'In death, life. In life, death. So goes the endless cycle of Phyrexia.');



-- ============================================================
-- LANDS
-- ============================================================

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('3c5e7a9b-1d3f-5c7e-9a1b-3d5f7c9e1a3b', 'Tundra', NULL, 0, 'Land — Plains Island', '{T}: Add {W} or {U}.', NULL, NULL, '', 'W,U', 'lea', 'Limited Edition Alpha', 'rare', NULL);

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('4d6f8b0c-2e4a-6d8f-0b2c-4e6a8d0f2b4c', 'Underground Sea', NULL, 0, 'Land — Island Swamp', '{T}: Add {U} or {B}.', NULL, NULL, '', 'U,B', 'lea', 'Limited Edition Alpha', 'rare', NULL);

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('5e7a9c1d-3f5b-7e9a-1c3d-5f7b9e1a3c5d', 'Volcanic Island', NULL, 0, 'Land — Island Mountain', '{T}: Add {U} or {R}.', NULL, NULL, '', 'U,R', 'lea', 'Limited Edition Alpha', 'rare', NULL);

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('6f8b0d2e-4a6c-8f0b-2d4e-6a8c0f2b4d6e', 'Fetchland (Flooded Strand)', NULL, 0, 'Land', '{T}, Pay 1 life, Sacrifice Flooded Strand: Search your library for a Plains or Island card, put it onto the battlefield, then shuffle.', NULL, NULL, '', 'W,U', 'ons', 'Onslaught', 'rare', NULL);

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('7a9c1e3f-5b7d-9a1c-3e5f-7b9d1a3c5e7f', 'Polluted Delta', NULL, 0, 'Land', '{T}, Pay 1 life, Sacrifice Polluted Delta: Search your library for an Island or Swamp card, put it onto the battlefield, then shuffle.', NULL, NULL, '', 'U,B', 'ons', 'Onslaught', 'rare', NULL);

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('8b0d2f4a-6c8e-0b2d-4f6a-8c0e2b4d6f8a', 'Scalding Tarn', NULL, 0, 'Land', '{T}, Pay 1 life, Sacrifice Scalding Tarn: Search your library for an Island or Mountain card, put it onto the battlefield, then shuffle.', NULL, NULL, '', 'U,R', 'zen', 'Zendikar', 'rare', 'The boiling waters strip flesh from bone before the tarn''s denizens have a chance to notice.');

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('9c1e3a5b-7d9f-1c3e-5a7b-9d1f3c5e7a9b', 'Misty Rainforest', NULL, 0, 'Land', '{T}, Pay 1 life, Sacrifice Misty Rainforest: Search your library for a Forest or Island card, put it onto the battlefield, then shuffle.', NULL, NULL, '', 'G,U', 'zen', 'Zendikar', 'rare', 'The jungle canopy extends as far as the eye can see, its trees drinking deep from the hidden aquifer below.');

INSERT INTO card (scryfall_id, name, mana_cost, cmc, type_line, oracle_text, power, toughness, colors, color_identity, set_code, set_name, rarity, flavor_text) VALUES
    ('0d2f4b6c-8e0a-2d4f-6b8c-0e2a4d6f8b0c', 'Windswept Heath', NULL, 0, 'Land', '{T}, Pay 1 life, Sacrifice Windswept Heath: Search your library for a Forest or Plains card, put it onto the battlefield, then shuffle.', NULL, NULL, '', 'G,W', 'ons', 'Onslaught', 'rare', NULL);