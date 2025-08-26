#!/usr/bin/env python3
"""
Simple test script to verify the backend search functionality
"""

import requests
import json

# Base URL for the event service
BASE_URL = "http://localhost:8080/api"

def test_search_endpoints():
    """Test the search endpoints"""
    
    print("Testing Event Search Endpoints...")
    print("=" * 50)
    
    # Test 1: Get all events
    print("\n1. Testing GET /events")
    try:
        response = requests.get(f"{BASE_URL}/events")
        if response.status_code == 200:
            events = response.json()
            print(f"✅ Success: Found {len(events)} events")
            if events:
                print(f"   Sample event: {events[0].get('name', 'N/A')}")
        else:
            print(f"❌ Failed: Status code {response.status_code}")
    except Exception as e:
        print(f"❌ Error: {e}")
    
    # Test 2: Search events
    print("\n2. Testing GET /events/search?query=concert")
    try:
        response = requests.get(f"{BASE_URL}/events/search?query=concert")
        if response.status_code == 200:
            events = response.json()
            print(f"✅ Success: Found {len(events)} events matching 'concert'")
            for event in events[:3]:  # Show first 3 results
                print(f"   - {event.get('name', 'N/A')}")
        else:
            print(f"❌ Failed: Status code {response.status_code}")
    except Exception as e:
        print(f"❌ Error: {e}")
    
    # Test 3: Search by category
    print("\n3. Testing GET /events/search/category?query=music&category=CONCERT")
    try:
        response = requests.get(f"{BASE_URL}/events/search/category?query=music&category=CONCERT")
        if response.status_code == 200:
            events = response.json()
            print(f"✅ Success: Found {len(events)} CONCERT events matching 'music'")
            for event in events[:3]:  # Show first 3 results
                print(f"   - {event.get('name', 'N/A')}")
        else:
            print(f"❌ Failed: Status code {response.status_code}")
    except Exception as e:
        print(f"❌ Error: {e}")
    
    # Test 4: Get upcoming events
    print("\n4. Testing GET /events/upcoming")
    try:
        response = requests.get(f"{BASE_URL}/events/upcoming")
        if response.status_code == 200:
            events = response.json()
            print(f"✅ Success: Found {len(events)} upcoming events")
            for event in events[:3]:  # Show first 3 results
                print(f"   - {event.get('name', 'N/A')} on {event.get('date', 'N/A')}")
        else:
            print(f"❌ Failed: Status code {response.statusCode}")
    except Exception as e:
        print(f"❌ Error: {e}")
    
    # Test 5: Search upcoming events
    print("\n5. Testing GET /events/search/upcoming?query=theater")
    try:
        response = requests.get(f"{BASE_URL}/events/search/upcoming?query=theater")
        if response.status_code == 200:
            events = response.json()
            print(f"✅ Success: Found {len(events)} upcoming events matching 'theater'")
            for event in events[:3]:  # Show first 3 results
                print(f"   - {event.get('name', 'N/A')}")
        else:
            print(f"❌ Failed: Status code {response.status_code}")
    except Exception as e:
        print(f"❌ Error: {e}")
    
    print("\n" + "=" * 50)
    print("Search endpoint testing completed!")

if __name__ == "__main__":
    test_search_endpoints()
