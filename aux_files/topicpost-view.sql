CREATE OR REPLACE VIEW public.topicpost
  AS
  SELECT p.id_post,
         p.image,
         p.latitude,
         p.longitude,
         p.media_url,
         p.text,
         p.video,
         p.user_id,
         p.created_at,
         p.modified_at,
         t.title,
         t.id AS id_topic,
         t.about_place_id,
         t.about_unity_id,
         t.topic_type_id
    FROM post p
    JOIN topic t ON t.id = p.id_post;