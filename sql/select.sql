select * from post order by id desc;

select * from member;
select * from project;

SELECT pid, title, description, start_date, end_date, image, status, creator_user, created_time
FROM project where pid = 29;

SELECT pid, title, description, start_date, end_date, image, status, creator_user, created_time, m.mname
  FROM project p inner join member m on p.creator_user = m.id 
  where pid = 11;

-- left join
-- 왼쪽 테이블은 필수적으로 있고, 오른쪽 테이블에는 없을 수도 있음
-- 둘 다 있으면 inner join 으로 처리
select *
from post p left join post_comment c on  p.id = c.post_id;

-- post의 id값을 기준으로 그룹핑
-- 그룹핑 열을 제외하고는 집계함수(count, sum, avg, max)
select p.id, p.title, p.content, p.created_date,
pf.nickname,
count(c.id) as commentCount
from post p 
	inner join profile pf on p.profile_id = pf.id
	left join post_comment c on  p.id = c.post_id
group by p.id, p.title, p.content, p.created_date,pf.nickname;

select * from post_comment where post_id = 1;

update project set creator_user = 1 where pid > 15;
update project set status = 2 where pid >= 10;
update project set status = 3 where pid >= 20;

SELECT pid, title, description, start_date, end_date, image, status, creator_user, created_time, 
		ptm.pid,   ptm.mid 
  FROM project p inner join project_team_member ptm on p.creator_user = ptm.id 
  where pid = 11;
  
SELECT   
 t2.title AS title,  
 t2.description AS description,  
 t2.start_date As startDate,  
 t2.end_date AS endDate,  
 t2.creator_user AS creatorUser,  
 t2.status,  
 t2.image,  
 t1.pid,  
 t1.mid  
FROM project_team_member t1  
INNER JOIN project t2 ON t1.pid = t2.pid  
  WHERE t1.mid = 1; 
  