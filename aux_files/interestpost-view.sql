CREATE OR REPLACE VIEW public.interestpost
  AS
  SELECT pi.posts_list_id_post AS post_id,
         pi.interests_list_id AS interest_id,
         i.name AS interest_name,
         p.modified_at
    FROM post p,
         interest i,
         post_interests_list pi
    WHERE i.id = pi.interests_list_id 
      AND p.id_post = pi.posts_list_id_post;