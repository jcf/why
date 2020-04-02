CREATE TABLE public.people (
    uuid uuid DEFAULT public.gen_random_uuid() NOT NULL,
    name text
);
COMMENT ON TABLE public.people IS 'Export this!';
ALTER TABLE ONLY public.people
    ADD CONSTRAINT people_pkey PRIMARY KEY (uuid);
