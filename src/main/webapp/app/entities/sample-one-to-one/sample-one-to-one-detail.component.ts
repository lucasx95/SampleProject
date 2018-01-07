import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { SampleOneToOne } from './sample-one-to-one.model';
import { SampleOneToOneService } from './sample-one-to-one.service';

@Component({
    selector: 'jhi-sample-one-to-one-detail',
    templateUrl: './sample-one-to-one-detail.component.html'
})
export class SampleOneToOneDetailComponent implements OnInit, OnDestroy {

    sampleOneToOne: SampleOneToOne;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private sampleOneToOneService: SampleOneToOneService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSampleOneToOnes();
    }

    load(id) {
        this.sampleOneToOneService.find(id).subscribe((sampleOneToOne) => {
            this.sampleOneToOne = sampleOneToOne;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSampleOneToOnes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'sampleOneToOneListModification',
            (response) => this.load(this.sampleOneToOne.id)
        );
    }
}
