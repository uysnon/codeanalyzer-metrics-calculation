select count(*)
from upload_designiteproject d
         inner join rank_rankscore r on d.id = r.designite_project_id
where d.is_open_access = true
  and r.last_ranked = 1
  and prog_language = 'java'
order by d.id;

select prog_language, count(*) from upload_designiteproject group by prog_language

update rank_rankscore r
set last_ranked = 1
where r.ranked_date = (select max(ranked_date) from rank_rankscore r1 where r1.designite_project_id = r.designite_project_id)

update rank_rankscore r
set last_ranked = 0
where != (select max(ranked_date) from rank_rankscore r1 where r1.designite_project_id = r.designite_project_id)
