DELETE FROM nodes;
DELETE FROM q_to_a_additional_links;
DELETE FROM quests;


ALTER TABLE quests ADD name text;