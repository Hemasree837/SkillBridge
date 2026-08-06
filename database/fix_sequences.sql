-- ==========================================================
-- Fix PostgreSQL sequence desync (duplicate key errors)
-- ==========================================================
-- Symptom: "duplicate key value violates unique constraint
--   'users_pkey' Key (id)=(3) already exists"
--
-- Cause: Sample data was imported with explicit IDs but the
--   underlying sequences were never advanced past those IDs,
--   so the next auto-generated id collides with an existing row.
--
-- This script resyncs each sequence to the current MAX(id) of
-- its table, so the next inserted row gets a brand-new id.
--
-- IMPORTANT: Run only once per environment. You can re-run it
-- safely anytime (it's idempotent).
-- ==========================================================

SELECT setval('public.users_id_seq', (SELECT COALESCE(MAX(id), 1) FROM public.users));
SELECT setval('public.skills_id_seq', (SELECT COALESCE(MAX(id), 1) FROM public.skills));
SELECT setval('public.swap_requests_id_seq', (SELECT COALESCE(MAX(id), 1) FROM public.swap_requests));
