INSERT INTO `test`.`user` (`user_id`)
SELECT * FROM (SELECT '1L') AS tmp
WHERE NOT EXISTS (
    SELECT user_id FROM `test`.`user` WHERE user_id = '1L'
) LIMIT 1;

INSERT INTO `test`.`user` (`user_id`)
SELECT * FROM (SELECT '2L') AS tmp
WHERE NOT EXISTS (
    SELECT user_id FROM `test`.`user` WHERE user_id = '2L'
) LIMIT 1;

INSERT INTO `test`.`user` (`user_id`)
SELECT * FROM (SELECT '3L') AS tmp
WHERE NOT EXISTS (
    SELECT user_id FROM `test`.`user` WHERE user_id = '3L'
) LIMIT 1;

INSERT INTO `test`.`user` (`user_id`)
SELECT * FROM (SELECT '4L') AS tmp
WHERE NOT EXISTS (
    SELECT user_id FROM `test`.`user` WHERE user_id = '4L'
) LIMIT 1;

INSERT INTO `test`.`user` (`user_id`)
SELECT * FROM (SELECT '5L') AS tmp
WHERE NOT EXISTS (
    SELECT user_id FROM `test`.`user` WHERE user_id = '5L'
) LIMIT 1;

INSERT INTO `test`.`user` (`user_id`)
SELECT * FROM (SELECT '6L') AS tmp
WHERE NOT EXISTS (
    SELECT user_id FROM `test`.`user` WHERE user_id = '6L'
) LIMIT 1;

INSERT INTO `test`.`user` (`user_id`)
SELECT * FROM (SELECT '7L') AS tmp
WHERE NOT EXISTS (
    SELECT user_id FROM `test`.`user` WHERE user_id = '7L'
) LIMIT 1;