import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { SampleMany } from './sample-many.model';
import { SampleManyService } from './sample-many.service';

@Component({
    selector: 'jhi-sample-many-detail',
    templateUrl: './sample-many-detail.component.html'
})
export class SampleManyDetailComponent implements OnInit, OnDestroy {

    sampleMany: SampleMany;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private sampleManyService: SampleManyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSampleManies();
    }

    load(id) {
        this.sampleManyService.find(id).subscribe((sampleMany) => {
            this.sampleMany = sampleMany;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSampleManies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'sampleManyListModification',
            (response) => this.load(this.sampleMany.id)
        );
    }
}
