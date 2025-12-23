#!/bin/bash
# Worktree 정리 스크립트

cd /Users/lwan/AndroidStudioProjects/SmartGlass

echo "=== 현재 Worktree 상태 ==="
git worktree list
echo ""

echo "=== Detached HEAD 상태인 Worktree 제거 ==="
git worktree list | grep "detached HEAD" | awk '{print $1}' | while read path; do
    echo "Removing: $path"
    git worktree remove "$path" --force 2>/dev/null || echo "  Failed to remove (may be in use)"
done

echo ""
echo "=== 정리 후 Worktree 목록 ==="
git worktree list

echo ""
echo "=== Prune 실행 ==="
git worktree prune

echo ""
echo "=== 최종 Worktree 목록 ==="
git worktree list
